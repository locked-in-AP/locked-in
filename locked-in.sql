-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 17, 2025 at 06:48 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `locked-in`
--

-- --------------------------------------------------------

--
-- Table structure for table `ORDER`
--

CREATE TABLE `ORDER` (
  `orderId` int(11) NOT NULL,
  `orderDate` datetime NOT NULL,
  `totalPrice` decimal(12,2) NOT NULL,
  `paymentDate` datetime DEFAULT NULL,
  `paymentMethod` varchar(50) DEFAULT NULL,
  `paymentAmount` decimal(12,2) DEFAULT NULL,
  `paymentStatus` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `PRODUCT`
--

CREATE TABLE `PRODUCT` (
  `productId` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `description` text DEFAULT NULL,
  `weight` decimal(8,2) DEFAULT NULL,
  `dimensions` varchar(100) DEFAULT NULL,
  `size` varchar(50) DEFAULT NULL,
  `color` varchar(50) DEFAULT NULL,
  `stockQuantity` int(11) DEFAULT 0,
  `discountRate` decimal(5,2) DEFAULT 0.00,
  `imageUrl` varchar(255) DEFAULT NULL,
  `category` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `USER`
--

CREATE TABLE `USER` (
  `userId` varchar(36) NOT NULL,
  `firstName` varchar(100) DEFAULT NULL,
  `middleName` varchar(100) DEFAULT NULL,
  `lastName` varchar(100) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `contactNum` varchar(20) DEFAULT NULL,
  `dateJoined` date DEFAULT NULL,
  `imageUrl` varchar(255) DEFAULT NULL,
  `cartSize` int(11) DEFAULT 0,
  `role` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `USER_PRODUCT`
--

CREATE TABLE `USER_PRODUCT` (
  `userId` varchar(36) NOT NULL,
  `productId` int(11) NOT NULL,
  `quantity` int(11) DEFAULT 1,
  `isCurrentlyInCart` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `USER_PRODUCT_ORDER`
--

CREATE TABLE `USER_PRODUCT_ORDER` (
  `userId` varchar(36) NOT NULL,
  `productId` int(11) NOT NULL,
  `orderId` int(11) NOT NULL,
  `orderQuantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `USER_PRODUCT_ORDER_REVIEW`
--

CREATE TABLE `USER_PRODUCT_ORDER_REVIEW` (
  `reviewId` int(11) NOT NULL,
  `userId` varchar(36) NOT NULL,
  `productId` int(11) NOT NULL,
  `orderId` int(11) NOT NULL,
  `reviewDate` datetime NOT NULL,
  `review` text DEFAULT NULL,
  `rating` int(11) DEFAULT NULL CHECK (`rating` >= 1 and `rating` <= 5)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ORDER`
--
ALTER TABLE `ORDER`
  ADD PRIMARY KEY (`orderId`);

--
-- Indexes for table `PRODUCT`
--
ALTER TABLE `PRODUCT`
  ADD PRIMARY KEY (`productId`);

--
-- Indexes for table `USER`
--
ALTER TABLE `USER`
  ADD PRIMARY KEY (`userId`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `USER_PRODUCT`
--
ALTER TABLE `USER_PRODUCT`
  ADD PRIMARY KEY (`userId`,`productId`),
  ADD KEY `productId` (`productId`);

--
-- Indexes for table `USER_PRODUCT_ORDER`
--
ALTER TABLE `USER_PRODUCT_ORDER`
  ADD PRIMARY KEY (`userId`,`productId`,`orderId`),
  ADD KEY `orderId` (`orderId`);

--
-- Indexes for table `USER_PRODUCT_ORDER_REVIEW`
--
ALTER TABLE `USER_PRODUCT_ORDER_REVIEW`
  ADD PRIMARY KEY (`reviewId`,`userId`,`productId`,`orderId`),
  ADD KEY `userId` (`userId`,`productId`,`orderId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `ORDER`
--
ALTER TABLE `ORDER`
  MODIFY `orderId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `PRODUCT`
--
ALTER TABLE `PRODUCT`
  MODIFY `productId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `USER_PRODUCT_ORDER_REVIEW`
--
ALTER TABLE `USER_PRODUCT_ORDER_REVIEW`
  MODIFY `reviewId` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `USER_PRODUCT`
--
ALTER TABLE `USER_PRODUCT`
  ADD CONSTRAINT `user_product_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `USER` (`userId`) ON DELETE CASCADE,
  ADD CONSTRAINT `user_product_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `PRODUCT` (`productId`) ON DELETE CASCADE;

--
-- Constraints for table `USER_PRODUCT_ORDER`
--
ALTER TABLE `USER_PRODUCT_ORDER`
  ADD CONSTRAINT `user_product_order_ibfk_1` FOREIGN KEY (`userId`,`productId`) REFERENCES `USER_PRODUCT` (`userId`, `productId`) ON DELETE CASCADE,
  ADD CONSTRAINT `user_product_order_ibfk_2` FOREIGN KEY (`orderId`) REFERENCES `ORDER` (`orderId`) ON DELETE CASCADE;

--
-- Constraints for table `USER_PRODUCT_ORDER_REVIEW`
--
ALTER TABLE `USER_PRODUCT_ORDER_REVIEW`
  ADD CONSTRAINT `user_product_order_review_ibfk_1` FOREIGN KEY (`userId`,`productId`,`orderId`) REFERENCES `USER_PRODUCT_ORDER` (`userId`, `productId`, `orderId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
