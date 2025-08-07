-- =====================================================
-- SCRIPT SQL - ESTRUCTURA EXACTA DEL USUARIO
-- Solo agregar limpieza de datos antes de inserts
-- =====================================================

-- =====================================================
-- TABLA: CLIENTE
-- =====================================================
CREATE TABLE IF NOT EXISTS CLIENTE (
                                       id_cliente INT AUTO_INCREMENT PRIMARY KEY,
                                       nombre VARCHAR(100) NOT NULL,
                                       email VARCHAR(100) NOT NULL UNIQUE,
                                       telefono VARCHAR(20),
                                       estrato INTEGER CHECK (estrato BETWEEN 1 AND 6),
                                       direccion VARCHAR(255),
                                       fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_cliente_email ON CLIENTE(email);
CREATE INDEX IF NOT EXISTS idx_cliente_estrato ON CLIENTE(estrato);

-- =====================================================
-- TABLA: PRODUCTO
-- =====================================================
CREATE TABLE IF NOT EXISTS PRODUCTO (
                                        id_producto INT AUTO_INCREMENT PRIMARY KEY,
                                        nombre VARCHAR(150) NOT NULL,
                                        descripcion VARCHAR(500),
                                        precio_base DECIMAL(10,2) NOT NULL CHECK (precio_base >= 0),
                                        categoria VARCHAR(50),
                                        activo BOOLEAN DEFAULT TRUE,
                                        peso_kg DECIMAL(8,3) DEFAULT 0.000,
                                        fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_producto_categoria ON PRODUCTO(categoria);
CREATE INDEX IF NOT EXISTS idx_producto_activo ON PRODUCTO(activo);
CREATE INDEX IF NOT EXISTS idx_producto_precio ON PRODUCTO(precio_base);

-- =====================================================
-- TABLA: REGLA_DESCUENTO
-- =====================================================
CREATE TABLE IF NOT EXISTS REGLA_DESCUENTO (
                                               id_regla_descuento INT AUTO_INCREMENT PRIMARY KEY,
                                               nombre_regla VARCHAR(100) NOT NULL,
                                               tipo_regla VARCHAR(20) NOT NULL CHECK (tipo_regla IN ('MONTO', 'BASADO_ESTRATO')),
                                               monto_minimo DECIMAL(10, 2) DEFAULT 0,
                                               valor_descuento DECIMAL(8, 2) NOT NULL CHECK (valor_descuento >= 0),
                                               activa BOOLEAN DEFAULT TRUE,
                                               fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_regla_activa ON REGLA_DESCUENTO(activa);

-- =====================================================
-- TABLA: PEDIDO
-- =====================================================
CREATE TABLE IF NOT EXISTS PEDIDO (
                                      id_pedido INT AUTO_INCREMENT PRIMARY KEY,
                                      id_cliente INT NOT NULL,
                                      subtotal DECIMAL(12,2) NOT NULL DEFAULT 0 CHECK (subtotal >= 0),
                                      costo_envio DECIMAL(8,2) NOT NULL DEFAULT 0,
                                      monto_descuento DECIMAL(10,2) NOT NULL DEFAULT 0,
                                      total DECIMAL(12,2) NOT NULL DEFAULT 0 CHECK (total >= 0),
                                      estado_pedido VARCHAR(20) DEFAULT 'PENDIENTE' CHECK (estado_pedido IN ('PENDIENTE', 'CONFIRMADO', 'ENVIADO', 'ENTREGADO', 'CANCELADO')),
                                      fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                      FOREIGN KEY (id_cliente) REFERENCES CLIENTE(id_cliente)
);

CREATE INDEX IF NOT EXISTS idx_pedido_cliente ON PEDIDO(id_cliente);
CREATE INDEX IF NOT EXISTS idx_pedido_estado ON PEDIDO(estado_pedido);
CREATE INDEX IF NOT EXISTS idx_pedido_fecha ON PEDIDO(fecha_creacion);

-- =====================================================
-- TABLA: ITEM_PEDIDO
-- =====================================================
CREATE TABLE IF NOT EXISTS ITEM_PEDIDO
(
    id_item_pedido  INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido       INT            NOT NULL,
    id_producto     INT            NOT NULL,
    cantidad        INT            NOT NULL CHECK (cantidad > 0),
    precio_unitario DECIMAL(10, 2) NOT NULL CHECK (precio_unitario >= 0),
    total_linea     DECIMAL(12, 2) NOT NULL CHECK (total_linea >= 0),
    fecha_creacion  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_pedido) REFERENCES PEDIDO (id_pedido) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES PRODUCTO (id_producto)
);

CREATE INDEX IF NOT EXISTS idx_item_pedido ON ITEM_PEDIDO (id_pedido);
CREATE INDEX IF NOT EXISTS idx_item_producto ON ITEM_PEDIDO (id_producto);

-- =====================================================
-- TABLA: USUARIO
-- =====================================================
CREATE TABLE IF NOT EXISTS USUARIO
(
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario       VARCHAR(100) NOT NULL UNIQUE,
    nombre              VARCHAR(100) NOT NULL,
    apellido            VARCHAR(100) NOT NULL,
    email               VARCHAR(100) NOT NULL UNIQUE,
    clave               VARCHAR(255) NOT NULL,
    habilitado          BOOLEAN   DEFAULT FALSE,
    fecha_creacion      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- LIMPIEZA DE DATOS EXISTENTES (orden por dependencias)
-- =====================================================
DELETE FROM ITEM_PEDIDO;
DELETE FROM PEDIDO;
DELETE FROM REGLA_DESCUENTO;
DELETE FROM PRODUCTO;
DELETE FROM CLIENTE;
DELETE FROM USUARIO;

-- Reiniciar secuencias de auto-increment
ALTER TABLE CLIENTE ALTER COLUMN id_cliente RESTART WITH 1;
ALTER TABLE PRODUCTO ALTER COLUMN id_producto RESTART WITH 1;
ALTER TABLE REGLA_DESCUENTO ALTER COLUMN id_regla_descuento RESTART WITH 1;
ALTER TABLE PEDIDO ALTER COLUMN id_pedido RESTART WITH 1;
ALTER TABLE ITEM_PEDIDO ALTER COLUMN id_item_pedido RESTART WITH 1;
ALTER TABLE USUARIO ALTER COLUMN id RESTART WITH 1;

-- =====================================================
-- DATOS DE EJEMPLO: CLIENTE
-- =====================================================
INSERT INTO CLIENTE (nombre, email, telefono, estrato, direccion)
VALUES ('Ana Martínez', 'ana.martinez@gmail.com', '3101234567', 3, 'Calle 45 #23-67, Bogotá'),
       ('Carlos Sánchez', 'carlos.sanchez@hotmail.com', '3202345678', 4, 'Carrera 78 #15-42, Medellín'),
       ('Laura Gómez', 'laura.gomez@outlook.com', '3153456789', 2, 'Avenida 68 #34-12, Cali'),
       ('Miguel Rodríguez', 'miguel.rodriguez@gmail.com', '3004567890', 5, 'Calle 120 #19-28, Barranquilla'),
       ('Sofía López', 'sofia.lopez@yahoo.com', '3175678901', 1, 'Carrera 15 #85-23, Bucaramanga');

-- =====================================================
-- DATOS DE EJEMPLO: PRODUCTO
-- =====================================================
INSERT INTO PRODUCTO (nombre, descripcion, precio_base, categoria, activo, peso_kg)
VALUES ('Laptop HP Pavilion', 'Laptop con procesador i5, 8GB RAM, 512GB SSD', 2500000.00, 'Computadores', TRUE, 1.800),
       ('Monitor Samsung 24"', 'Monitor LED Full HD con panel IPS', 750000.00, 'Periféricos', TRUE, 3.500),
       ('Teclado Mecánico RGB', 'Teclado gaming con switches Cherry MX', 320000.00, 'Periféricos', TRUE, 0.950),
       ('Smartphone Xiaomi', 'Smartphone con 6GB RAM, 128GB almacenamiento', 1200000.00, 'Celulares', TRUE, 0.180),
       ('Audífonos Bluetooth', 'Audífonos inalámbricos con cancelación de ruido', 180000.00, 'Audio', TRUE, 0.250),
       ('Mouse Inalámbrico', 'Mouse ergonómico de alta precisión', 95000.00, 'Periféricos', TRUE, 0.120),
       ('Tablet Samsung', 'Tablet de 10 pulgadas con 64GB', 950000.00, 'Tablets', TRUE, 0.450),
       ('Impresora Multifuncional', 'Impresora, escáner y copiadora', 480000.00, 'Impresoras', TRUE, 5.200),
       ('Disco Duro Externo 1TB', 'Almacenamiento portátil USB 3.0', 220000.00, 'Almacenamiento', TRUE, 0.300),
       ('Router WiFi', 'Router de doble banda con 4 antenas', 150000.00, 'Redes', TRUE, 0.400);

-- =====================================================
-- DATOS DE EJEMPLO: REGLA_DESCUENTO
-- =====================================================
INSERT INTO REGLA_DESCUENTO (nombre_regla, tipo_regla, monto_minimo, valor_descuento, activa)
VALUES ('Descuento por Compra Mayor', 'MONTO', 2000000.00, 50000.00, TRUE),
       ('Descuento Estrato 1-2', 'BASADO_ESTRATO', 500000.00, 30000.00, TRUE);

-- =====================================================
-- DATOS DE EJEMPLO: USUARIO
-- =====================================================
INSERT INTO USUARIO (nombre_usuario, nombre, apellido, email, clave, habilitado)
VALUES ('admin', 'Administrador', 'Sistema', 'admin@sistema.com',
        '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', TRUE),
       ('usuario1', 'Juan', 'Pérez', 'juan.perez@email.com',
        '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', TRUE),
       ('usuario2', 'María', 'López', 'maria.lopez@email.com',
        '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', TRUE);