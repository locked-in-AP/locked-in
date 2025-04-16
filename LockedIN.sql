-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 16, 2025 at 06:23 PM
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
-- Database: `LockedIN`
--

-- --------------------------------------------------------

--
-- Table structure for table `CATEGORY`
--

CREATE TABLE `CATEGORY` (
  `categoryId` int(11) NOT NULL,
  `category` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ORDERS`
--

CREATE TABLE `ORDERS` (
  `orderId` int(11) NOT NULL,
  `orderDate` datetime NOT NULL DEFAULT current_timestamp(),
  `totalPrice` decimal(12,2) NOT NULL,
  `paymentDate` datetime DEFAULT NULL,
  `paymentMethod` varchar(50) DEFAULT NULL,
  `paymentAmount` decimal(12,2) DEFAULT NULL,
  `paymentStatus` varchar(50) NOT NULL DEFAULT 'Pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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
  `stockQuantity` int(11) NOT NULL DEFAULT 0,
  `discountRate` decimal(5,2) DEFAULT 0.00,
  `imageUrl` varchar(255) DEFAULT NULL,
  `categoryId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ROLE`
--

CREATE TABLE `ROLE` (
  `roleId` int(11) NOT NULL,
  `role` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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
  `discountRate` decimal(5,2) DEFAULT 0.00,
  `roleId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `USER_PRODUCT`
--

CREATE TABLE `USER_PRODUCT` (
  `userId` varchar(36) NOT NULL,
  `productId` int(11) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT 1 CHECK (`quantity` > 0),
  `isCurrentlyInCart` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `USER_PRODUCT_ORDER`
--

CREATE TABLE `USER_PRODUCT_ORDER` (
  `userId` varchar(36) NOT NULL,
  `productId` int(11) NOT NULL,
  `orderId` int(11) NOT NULL,
  `orderQuantity` int(11) NOT NULL CHECK (`orderQuantity` > 0),
  `priceAtOrder` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `USER_PRODUCT_ORDER_REVIEW`
--

CREATE TABLE `USER_PRODUCT_ORDER_REVIEW` (
  `reviewId` int(11) NOT NULL,
  `userId` varchar(36) NOT NULL,
  `productId` int(11) NOT NULL,
  `orderId` int(11) NOT NULL,
  `reviewDate` datetime NOT NULL DEFAULT current_timestamp(),
  `review` text DEFAULT NULL,
  `rating` int(11) DEFAULT NULL CHECK (`rating` >= 1 and `rating` <= 5)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `CATEGORY`
--
ALTER TABLE `CATEGORY`
  ADD PRIMARY KEY (`categoryId`),
  ADD UNIQUE KEY `category` (`category`);

--
-- Indexes for table `ORDERS`
--
ALTER TABLE `ORDERS`
  ADD PRIMARY KEY (`orderId`);

--
-- Indexes for table `PRODUCT`
--
ALTER TABLE `PRODUCT`
  ADD PRIMARY KEY (`productId`),
  ADD KEY `categoryId` (`categoryId`);

--
-- Indexes for table `ROLE`
--
ALTER TABLE `ROLE`
  ADD PRIMARY KEY (`roleId`),
  ADD UNIQUE KEY `role` (`role`);

--
-- Indexes for table `USER`
--
ALTER TABLE `USER`
  ADD PRIMARY KEY (`userId`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `roleId` (`roleId`);

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
  ADD KEY `productId` (`productId`),
  ADD KEY `orderId` (`orderId`);

--
-- Indexes for table `USER_PRODUCT_ORDER_REVIEW`
--
ALTER TABLE `USER_PRODUCT_ORDER_REVIEW`
  ADD PRIMARY KEY (`reviewId`),
  ADD UNIQUE KEY `uq_user_product_order` (`userId`,`productId`,`orderId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `CATEGORY`
--
ALTER TABLE `CATEGORY`
  MODIFY `categoryId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `ORDERS`
--
ALTER TABLE `ORDERS`
  MODIFY `orderId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `PRODUCT`
--
ALTER TABLE `PRODUCT`
  MODIFY `productId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `ROLE`
--
ALTER TABLE `ROLE`
  MODIFY `roleId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `USER_PRODUCT_ORDER_REVIEW`
--
ALTER TABLE `USER_PRODUCT_ORDER_REVIEW`
  MODIFY `reviewId` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `PRODUCT`
--
ALTER TABLE `PRODUCT`
  ADD CONSTRAINT `product_ibfk_1` FOREIGN KEY (`categoryId`) REFERENCES `CATEGORY` (`categoryId`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `USER`
--
ALTER TABLE `USER`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `ROLE` (`roleId`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `USER_PRODUCT`
--
ALTER TABLE `USER_PRODUCT`
  ADD CONSTRAINT `user_product_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `USER` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_product_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `PRODUCT` (`productId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `USER_PRODUCT_ORDER`
--
ALTER TABLE `USER_PRODUCT_ORDER`
  ADD CONSTRAINT `user_product_order_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `USER` (`userId`) ON UPDATE CASCADE,
  ADD CONSTRAINT `user_product_order_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `PRODUCT` (`productId`) ON UPDATE CASCADE,
  ADD CONSTRAINT `user_product_order_ibfk_3` FOREIGN KEY (`orderId`) REFERENCES `ORDERS` (`orderId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `USER_PRODUCT_ORDER_REVIEW`
--
ALTER TABLE `USER_PRODUCT_ORDER_REVIEW`
  ADD CONSTRAINT `user_product_order_review_ibfk_1` FOREIGN KEY (`userId`,`productId`,`orderId`) REFERENCES `USER_PRODUCT_ORDER` (`userId`, `productId`, `orderId`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
