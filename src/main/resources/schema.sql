-- 用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    address VARCHAR(200),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 商品表
CREATE TABLE IF NOT EXISTS inventory (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    stock INT NOT NULL DEFAULT 0,
    price DECIMAL(10,2) NOT NULL,
    min_stock INT NOT NULL DEFAULT 0,
    sales_count INT NOT NULL DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 订单表
CREATE TABLE IF NOT EXISTS sale_orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    user_id BIGINT,
    order_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
); 

-- 供应商表
CREATE TABLE IF NOT EXISTS suppliers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    address VARCHAR(200),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 商品-供应商关联表
CREATE TABLE IF NOT EXISTS product_suppliers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(100) NOT NULL,
    supplier_id BIGINT NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id),
    UNIQUE KEY unique_product (product_name)
);

-- 采购订单表
CREATE TABLE IF NOT EXISTS purchase_orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    supplier_id BIGINT NOT NULL,
    purchase_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id)
);

DELIMITER //
CREATE TRIGGER auto_restock_trigger 
AFTER UPDATE ON inventory
FOR EACH ROW
BEGIN
    DECLARE v_supplier_id BIGINT;
    
    -- 只在库存小于最小库存时触发补货
    IF NEW.stock < NEW.min_stock THEN
        -- 查找供应商ID
        SELECT supplier_id INTO v_supplier_id 
        FROM product_suppliers 
        WHERE product_name = NEW.name;
        
        IF v_supplier_id IS NOT NULL THEN
            -- 记录采购信息
            INSERT INTO purchase_orders (product_name, quantity, supplier_id)
            VALUES (NEW.name, 100, v_supplier_id);
            
            -- 使用独立的更新语句更新库存
            SET NEW.stock = NEW.stock + 100;
        END IF;
    END IF;
END //
DELIMITER ;
-- 插入测试数据
INSERT IGNORE INTO suppliers (name, phone, address) VALUES 
('华为科技', '13800138000', '深圳市龙岗区'),
('小米科技', '13900139000', '北京市海淀区'),
('OPPO电子', '13700137000', '东莞市长安镇');

-- 关联商品和供应商
INSERT IGNORE INTO product_suppliers (product_name, supplier_id) VALUES 
('智能手表 Pro', 1),
('无线耳机 Max', 2),
('便携充电宝', 3);
