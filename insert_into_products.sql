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
('Locked IN Performance Crew Socks 5-Pack', 'High-performance athletic socks with moisture-wicking technology', 'Locked IN', 'merchandise', 'socks,clothing,accessories', 35.00, 200, 0.5, '${pageContext.request.contextPath}/resources/images/system/sample.png', '10x6x1'),

('Locked IN Training Shorts', 'Premium athletic shorts with built-in compression liner', 'Locked IN', 'merchandise', 'shorts,clothing,training', 45.00, 150, 0.3, '${pageContext.request.contextPath}/resources/images/system/sample.png', '12x8x1'),

('Locked IN Performance Tank Top', 'Lightweight performance tank top with quick-dry technology', 'Locked IN', 'merchandise', 'tank top,clothing,training', 40.00, 180, 0.2, '${pageContext.request.contextPath}/resources/images/system/sample.png', '14x10x1'); 