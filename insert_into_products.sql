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
('Thavage Pre-Workout', 'High-energy pre-workout formula with focus and pump enhancers', 'Raw Nutrition', 'supplement', 'pre-workout,energy,focus', 44.99, 120, 0.8, 'https://getrawnutrition.com/cdn/shop/files/thavage_20220622_02.png?v=1683235698&width=1512', '6x6x4'),
('CBUM Itholate Protein - Chocolate', 'Premium whey protein isolate in rich chocolate flavor', 'Raw Nutrition', 'supplement', 'protein,whey,isolate,chocolate', 54.99, 85, 2.0, 'https://getrawnutrition.com/cdn/shop/products/raw-nutrition--cbum-itholate-protein-chocolate-peanut-butter-25-servings-28297541173372_1080x.jpg?v=1671831040', '8x8x4'),
('Raw EAAs', 'Essential amino acid formula for improved recovery and muscle preservation', 'Raw Nutrition', 'supplement', 'amino acids,recovery,muscle', 39.99, 110, 0.7, 'https://getrawnutrition.com/cdn/shop/products/Raw-4x4-EAA-Mango_1024x1024.jpg?v=1612469042', '5x5x3'),
('Growth Hormone Support', 'Natural supplement designed to optimize hormone levels', 'Raw Nutrition', 'supplement', 'hormone,growth,recovery', 59.99, 70, 0.5, 'https://getrawnutrition.com/cdn/shop/products/Raw-4x4-GH-Front_1024x1024.jpg?v=1612469044', '4x4x3'),
('Test Elite Testosterone Support', 'Premium testosterone support formula with key vitamins and minerals', 'Raw Nutrition', 'supplement', 'testosterone,hormones,vitality', 64.99, 65, 0.6, 'https://getrawnutrition.com/cdn/shop/files/TEST-Front.jpg?v=1683670202&width=1512', '4x4x3'),
('Raw Nutrition Creatine Monohydrate', 'Pharmaceutical grade creatine for strength and muscle development', 'Raw Nutrition', 'supplement', 'creatine,strength,recovery', 34.99, 150, 0.5, 'https://getrawnutrition.com/cdn/shop/products/raw-nutrition-creatine-monohydrate-60-servings-28297542156412_1080x.jpg?v=1671831081', '5x5x3'),
('CBUM Thavage Pre-Workout Stick Packs', 'Convenient single-serving pre-workout packs for on-the-go energy', 'Raw Nutrition', 'supplement', 'pre-workout,energy,portable', 49.99, 90, 0.4, 'https://getrawnutrition.com/cdn/shop/files/CBUM-Thavage-Pre-Workout-Stick-Packs-Tiger-s-Blood_d5e5dd65-d3b2-4af0-90ae-cca15adbf17a.jpg?v=1683670236&width=1512', '7x5x2'),

-- Additional Equipment
('NÜOBELL Adjustable Dumbbells', 'Space-saving adjustable dumbbells with quick weight change mechanism', 'NÜOBELL', 'equipment', 'dumbbells,adjustable,home gym', 745.00, 30, 80.0, 'https://www.topfitness.com/cdn/shop/products/nuobell-adjustable-dumbbells-2x80lbs-661889_800x.jpg?v=1677882583', '16x8x9'),
('SkiErg 2 Machine', 'Full-body cardio training machine that simulates Nordic skiing', 'Concept2', 'equipment', 'cardio,skiing,full-body', 1095.00, 15, 46.0, 'https://www.topfitness.com/cdn/shop/products/concept2-skierg-with-pm5-performance-monitor-811954_800x.jpg?v=1678291683', '22x24x83'),
('Rogue Echo Bike', 'Heavy-duty air bike for high-intensity interval training', 'Rogue', 'equipment', 'bike,cardio,hiit', 795.00, 20, 127.0, 'https://www.roguefitness.com/media/catalog/product/cache/1/image/1500x1500/472321edac810f9b2465a359d8cdc0b5/e/c/echo-bike-web1.jpg', '45x24x52'),
('Force USA G20 All-In-One Trainer', 'Commercial grade multi-functional strength training system', 'Force USA', 'equipment', 'strength,functional,commercial', 5999.00, 5, 960.0, 'https://www.topfitness.com/cdn/shop/products/force-usa-g20-all-in-one-trainer-799498_800x.jpg?v=1678291589', '87x63x91'),
('Strength Shop Olympic Weightlifting Bar', 'Competition-grade 20kg Olympic weightlifting barbell', 'Strength Shop', 'equipment', 'barbell,olympic,weightlifting', 349.00, 40, 20.0, 'https://www.strengthshop.eu/cdn/shop/products/20kgblackpowerbar_550x.jpg?v=1697556217', '86x2x2'),
('Titan Fitness Lat Tower', 'Commercial-grade lat pulldown tower for back development', 'Titan Fitness', 'equipment', 'lat pulldown,back,strength', 1099.99, 12, 210.0, 'https://cdn.shopify.com/s/files/1/0508/0371/9297/products/LATMACH_3.jpg?v=1639686318', '48x36x84'),
('GHD - Glute Ham Developer', 'Professional glute-ham developer for posterior chain strength', 'Rogue', 'equipment', 'glutes,hamstrings,posterior chain', 795.00, 18, 172.0, 'https://www.roguefitness.com/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/a/b/abram-ghd-2.0-web8.jpg', '56x30x40'),

-- Additional Merchandise
('Locked IN Performance Joggers', 'Athletic jogger pants with tapered fit and stretch fabric', 'Locked IN', 'merchandise', 'joggers,pants,clothing', 65.00, 160, 0.7, 'https://cdn.shopify.com/s/files/1/0156/6146/files/ReverseCutHoodieBlack_RawCutJoggerBlackValleChallengeSliders4G3A8-BBKJ_2_3840x.jpg?v=1745401450', '16x12x1'),
('Locked IN Adjustable Cap', 'Premium athletic cap with moisture-wicking sweatband', 'Locked IN', 'merchandise', 'cap,hat,accessories', 28.99, 200, 0.2, 'https://cdn.shopify.com/s/files/1/0156/6146/files/QuadrantCapBlackALC1A-BBBO_1_3840x.jpg?v=1743812329', '10x8x4'),
('Locked IN Performance Hoodie', 'Heavyweight athletic hoodie with soft interior lining', 'Locked IN', 'merchandise', 'hoodie,clothing,outerwear', 75.00, 120, 0.9, 'https://cdn.shopify.com/s/files/1/0156/6146/files/PowerOriginalsCutOffOversizedHoodieGreyMarlA2A2M-BBFA_2_3840x.jpg?v=1745400992', '20x16x2'),
('Locked IN Performance Lifting Straps', 'Heavy-duty lifting straps for maximum grip support', 'Locked IN', 'merchandise', 'straps,accessories,lifting', 19.99, 250, 0.2, 'https://cdn.shopify.com/s/files/1/0156/6146/files/GripcoreWristWraps-BlackE4A1Y-BBBB_1_3840x.jpg?v=1742894704', '8x3x1'),
('Locked IN 32oz Water Bottle', 'BPA-free large capacity water bottle with time markers', 'Locked IN', 'merchandise', 'bottle,hydration,accessories', 25.00, 180, 0.4, 'https://cdn.shopify.com/s/files/1/0156/6146/files/GymsharkShaker1LBeachHouseBlueE8A1Y-BBDV_1_3840x.jpg?v=1745401456', '3.5x3.5x10'),
('Locked IN Dual Compartment Gym Bag', 'Premium gym bag with separate compartments for shoes and clothes', 'Locked IN', 'merchandise', 'bag,accessories,storage', 89.99, 70, 1.5, 'https://cdn.shopify.com/s/files/1/0156/6146/files/GS-FW22-Accessories-4247_3840x.jpg?v=1679330507', '22x12x12'),
('Locked IN Training Gloves', 'Lightweight training gloves with silicone grip pads', 'Locked IN', 'merchandise', 'gloves,accessories,training', 29.99, 140, 0.3, 'https://cdn.shopify.com/s/files/1/0156/6146/files/GripcoreTrainingGloves-BlackE4A3Y-BBBB_1_3840x.jpg?v=1742894685', '9x5x1');