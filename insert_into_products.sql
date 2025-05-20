-- Insert sample products for each category
INSERT INTO product (name, description, brand, category, tags, price, stock_quantity, weight, image, dimensions) VALUES
-- Supplements
('CBUM Itholate Protein', 'Premium whey protein isolate for maximum muscle growth and recovery', 'Raw Nutrition', 'supplement', 'protein,whey,isolate', 55.00, 100, 2.0, 'https://getrawnutrition.com/cdn/shop/files/Bum_Itholate_Toathted_Graham_Cracker_25_serv_-_front_312e015a-6614-4544-a1c7-d55c0ff2639e.png?v=1742227696&width=1512', '8x8x4'),

('5lb CBUM Itholate Protein', 'Premium whey protein isolate in bulk size for serious athletes', 'Raw Nutrition', 'supplement', 'protein,whey,isolate,bulk', 99.99, 50, 5.0, 'https://getrawnutrition.com/cdn/shop/files/5_Pounds_Vanilla_Oatmeal_Front_1.jpg?v=1743604298&width=900', '10x10x6'),

('Christopher''s Secret Whey Itholate Protein', 'Signature whey protein isolate with unique flavor profile', 'Raw Nutrition', 'supplement', 'protein,whey,isolate,premium', 54.99, 75, 2.0, 'https://getrawnutrition.com/cdn/shop/files/BUM-Itholate-Chris_s_Secret_Whey_Blueberry_muffin.png?v=1731361695&width=1512', '8x8x4'),

-- Equipment
('French Fitness Rubber Coated Hex Dumbbell Set', 'Professional grade rubber coated hex dumbbells for home and commercial use', 'French Fitness', 'equipment', 'dumbbells,weights,strength', 55.00, 50, 50.0, 'https://fitnesssuperstore.com/cdn/shop/files/FF-RCHD5-50-2.png?v=1739353976&width=460', '24x12x12'),

('Smith Functional Trainer', 'Professional grade functional trainer with Smith machine capabilities', 'Inspire', 'equipment', 'functional trainer,smith machine,strength', 6499.00, 10, 500.0, 'https://www.topfitness.com/cdn/shop/files/inspire-ft2-pro-smith-functional-trainer-771744.jpg?v=1726811262', '72x48x84'),

('Centr x Hyrox Perform Tread', 'High-performance treadmill designed for serious athletes', 'Centr', 'equipment', 'treadmill,cardio,professional', 6400.00, 8, 300.0, 'https://www.topfitness.com/cdn/shop/files/centr-x-hyrox-perform-tread-8_f2282a75-1abe-484c-9ef9-60a27ae1555a.jpg?v=1739048346', '84x36x48'),

-- Merchandise
('Locked IN Performance Crew Socks 5-Pack', 'High-performance athletic socks with moisture-wicking technology', 'Locked IN', 'merchandise', 'socks,clothing,accessories', 35.00, 200, 0.5, 'https://cdn.shopify.com/s/files/1/0156/6146/files/CrewSocks5pkGSClassicBlue_GSRestBlue_GSMetalPurple_GSGentleBlue_GSSoftWhiteI3A1Y-UDC1131-0041_512c25ba-7064-41fb-900f-ea19c8592019_3840x.jpg?v=1742894737', '10x6x1'),

('Locked IN Training Shorts', 'Premium athletic shorts with built-in compression liner', 'Locked IN', 'merchandise', 'shorts,clothing,training', 45.00, 150, 0.3, 'https://www.gymshark.com/_next/image?url=https%3A%2F%2Fimages.ctfassets.net%2Fwl6q2in9o7k3%2F7sq3VZZa2AgEuTO0xgpP9n%2F34ccbb5239240aa648593412262f843b%2Fflared.webp&w=3840&q=75', '12x8x1'),

('Locked IN Performance Tank Top', 'Lightweight performance tank top with quick-dry technology', 'Locked IN', 'merchandise', 'tank top,clothing,training', 40.00, 180, 0.2, 'https://cdn.shopify.com/s/files/1/0156/6146/files/A_PowerOriginalsCutOffTankCharcoalCoreMarlA2C9M-GBBB_1027_3840x.jpg?v=1745401454', '14x10x1'); 


INSERT INTO product (name, description, brand, category, tags, price, stock_quantity, weight, image, dimensions) VALUES
-- Additional Supplements
('Thavage Pre-Workout', 'High-energy pre-workout formula with focus and pump enhancers', 'Raw Nutrition', 'supplement', 'pre-workout,energy,focus', 44.99, 120, 0.8, 'https://cdn.shopify.com/s/files/1/0932/3141/5614/files/BELCAST-thavage.webp?v=1746461059&width=1080', '6x6x4'),
('Protein Isolate RTD', 'Grass-Fed Isolate Protein RTD (12 Pack)', 'Raw Nutrition', 'supplement', 'protein,whey,isolate,chocolate', 54.99, 85, 2.0, 'https://cdn.shopify.com/s/files/1/0932/3141/5614/files/RTD_BOTTLE_BOX_CHOC_53a5b006-3124-4e57-b516-5a7409d0c6a7.webp?v=1746041581&width=1080', '8x8x4'),
('Raw EAAs', 'Essential amino acid formula for improved recovery and muscle preservation', 'Raw Nutrition', 'supplement', 'amino acids,recovery,muscle', 39.99, 110, 0.7, 'https://cdn.shopify.com/s/files/1/0932/3141/5614/files/EAA_Strawberry_Lemonade_Front_16e6c736-e5e3-4e69-89e6-b4b057b72854.webp?v=1745899383&width=1080', '5x5x3'),
('Raw Sleep Formula Powder with NooGandha® | 30 Servings', 'Shop Recovery Supplements and speed up the recovery process by incorporating high quality premium products into your stack.', 'Raw Nutrition', 'supplement', 'hormone,growth,recovery', 59.99, 70, 0.5, 'https://cdn.shopify.com/s/files/1/0932/3141/5614/files/Sleep_Mixed_Berry_New_0eae987d-0eb1-4d6f-8e8c-75bd45225199.webp?v=1745899392&width=1080', '4x4x3'),
('Test Elite Testosterone Support', 'Premium testosterone support formula with key vitamins and minerals', 'Raw Nutrition', 'supplement', 'testosterone,hormones,vitality', 64.99, 65, 0.6, 'https://m.media-amazon.com/images/I/71E4Sh-OKjL._AC_SL1500_.jpg', '4x4x3'),
('Raw Nutrition Creatine Monohydrate', 'Pharmaceutical grade creatine for strength and muscle development', 'Raw Nutrition', 'supplement', 'creatine,strength,recovery', 34.99, 150, 0.5, 'https://cdn.shopify.com/s/files/1/0932/3141/5614/files/Creatine_30_serv_render.webp?v=1745957991&width=1843', '5x5x3'),
('CBUM Thavage Pre-Workout', 'Convenient single-serving pre-workout packs for on-the-go energy', 'Raw Nutrition', 'supplement', 'pre-workout,energy,portable', 49.99, 90, 0.4, 'https://cdn.shopify.com/s/files/1/0932/3141/5614/files/ThavageRTDStrawberryMango.webp?v=1745956822&width=1080', '7x5x2'),

-- Additional Equipment
('NÜOBELL Adjustable Dumbbells', 'Space-saving adjustable dumbbells with quick weight change mechanism', 'NÜOBELL', 'equipment', 'dumbbells,adjustable,home gym', 745.00, 30, 80.0, 'https://m.media-amazon.com/images/I/81CQTBt6xQL._AC_UF1000,1000_QL80_.jpg', '16x8x9'),
('SkiErg 2 Machine', 'Full-body cardio training machine that simulates Nordic skiing', 'Concept2', 'equipment', 'cardio,skiing,full-body', 1095.00, 15, 46.0, 'https://treadmillfactory.ca/cdn/shop/files/SKI_ERG_BLACK-00.png?v=17235792203', '22x24x83'),
('Rogue Echo Bike', 'Heavy-duty air bike for high-intensity interval training', 'Rogue', 'equipment', 'bike,cardio,hiit', 795.00, 20, 127.0, 'https://assets.roguefitness.com/f_auto,q_auto,c_limit,w_1600,b_rgb:ffffff/catalog/Conditioning/Endurance%20/Bikes/ECHOBIKE/ECHOBIKE-TH_ih5rzy.png', '45x24x52'),
('Force USA G20 All-In-One Trainer', 'Commercial grade multi-functional strength training system', 'Force USA', 'equipment', 'strength,functional,commercial', 5999.00, 5, 960.0, 'https://www.forceusa.com/cdn/shop/files/G20-fulldress-front-left.jpg?v=1730408889', '87x63x91'),
('Strength Shop Olympic Weightlifting Bar', 'Competition-grade 20kg Olympic weightlifting barbell', 'Strength Shop', 'equipment', 'barbell,olympic,weightlifting', 349.00, 40, 20.0, 'https://strengthshop.eu/cdn/shop/products/BAR-2029-BLK-CHROME-SLEEVES_Shopo_x1_314c143d-b28e-4a8a-86b5-f64c5d855625.jpg?v=1678355205', '86x2x2'),
('Titan Fitness Lat Tower', 'Commercial-grade lat pulldown tower for back development', 'Titan Fitness', 'equipment', 'lat pulldown,back,strength', 1099.99, 12, 210.0, 'https://titan.fitness/cdn/shop/files/401756_01.jpg?v=1743543994', '48x36x84'),
('GHD - Glute Ham Developer', 'Professional glute-ham developer for posterior chain strength', 'Rogue', 'equipment', 'glutes,hamstrings,posterior chain', 795.00, 18, 172.0, 'https://www.alphafit.com.au/assets/full/50652.jpg?20240918133948', '56x30x40'),

-- Additional Merchandise
('Locked IN Performance Joggers', 'Athletic jogger pants with tapered fit and stretch fabric', 'Locked IN', 'merchandise', 'joggers,pants,clothing', 65.00, 160, 0.7, 'https://cdn.shopify.com/s/files/1/1367/5201/files/TRAININGPERFORMANCEJOGGERSBlackB3A7T-BBBB-1051_3840x.jpg?v=1713345094', '16x12x1'),
('Locked IN Adjustable Cap', 'Premium athletic cap with moisture-wicking sweatband', 'Locked IN', 'merchandise', 'cap,hat,accessories', 28.99, 200, 0.2, 'https://cdn.shopify.com/s/files/1/1367/5201/files/ElevateCapGSBlackI3A9X-BB2J-1437_3840x.jpg?v=1725290071', '10x8x4'),
('Locked IN Performance Hoodie', 'Heavyweight athletic hoodie with soft interior lining', 'Locked IN', 'merchandise', 'hoodie,clothing,outerwear', 75.00, 120, 0.9, 'https://cdn.shopify.com/s/files/1/1367/5201/files/CrestHoodieBlackA2A4G-BBBB-1259-Edit_3840x.jpg?v=1743417063', '20x16x2'),
('Locked IN Performance Lifting Straps', 'Heavy-duty lifting straps for maximum grip support', 'Locked IN', 'merchandise', 'straps,accessories,lifting', 19.99, 250, 0.2, 'https://cdn.shopify.com/s/files/1/1367/5201/files/LiftingStraps_StrongmanEtc_BlackI1A5A.B_ZH_ZH_e305f92a-87b7-4a60-99c2-ec48d00ce4f4_3840x.jpg?v=1739780670', '8x3x1'),
('Locked IN 32oz Water Bottle', 'BPA-free large capacity water bottle with time markers', 'Locked IN', 'merchandise', 'bottle,hydration,accessories', 25.00, 180, 0.4, 'https://cdn.shopify.com/s/files/1/1367/5201/files/2.2LWaterBottleBlackI1A2U-BBBB8394_3840x.jpg?v=1724055195', '3.5x3.5x10'),
('Locked IN Dual Compartment Gym Bag', 'Premium gym bag with separate compartments for shoes and clothes', 'Locked IN', 'merchandise', 'bag,accessories,storage', 89.99, 70, 1.5, 'https://cdn.shopify.com/s/files/1/1367/5201/files/EverydayHoldallGSMutedPinkI3A1V-KB68-1880-0246_b9e1bfbd-6b45-453b-833d-63be392d521c_3840x.jpg?v=1744645449', '22x12x12'),
('Locked IN Training Gloves', 'Lightweight training gloves with silicone grip pads', 'Locked IN', 'merchandise', 'gloves,accessories,training', 29.99, 140, 0.3, 'https://cdn.shopify.com/s/files/1/1367/5201/files/LegacyLiftingGlovesBlackI1A2S-BBBB29-Edit_BK_061a0acd-744b-433d-8d9e-096526271907_3840x.jpg?v=1740429635', '9x5x1');