-- Add profile_picture column to users table
ALTER TABLE users
ADD COLUMN profile_picture VARCHAR(255) DEFAULT 'resources/images/system/userpfp.png'; 