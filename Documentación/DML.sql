CREATE EXTENSION IF NOT EXISTS pgcrypto;
DELETE FROM users;
DELETE FROM roles;
DELETE FROM products;
DELETE FROM categories;

INSERT INTO roles (name_role, description) VALUES
('ADMINISTRADOR', 'Administrador del sistema'),
('MODERADOR', 'Encargado de aprobar productos o sancionar usuarios comunes'),
('LOGÍSTICA', 'Encargado de la logística y envíos'),
('USUARIO COMÚN', 'Usuario común del sistema que puede comprar o vender productos');

INSERT INTO categories (category_name, description) VALUES
('Tecnología', 'Productos electrónicos, computadoras, celulares y accesorios tecnológicos.'),
('Hogar', 'Artículos para el hogar como muebles, electrodomésticos y utensilios.'),
('Académico', 'Material de estudio, libros, papelería y artículos escolares.'),
('Personal', 'Productos de uso personal como ropa, calzado, higiene y belleza.'),
('Decoración', 'Objetos decorativos para el hogar u oficina.'),
('Otro', 'Productos varios que no encajan en otras categorías.');

INSERT INTO users(user_dpi, address, email, name, password, status, id_role) VALUES
--ADMINISTRADOR
('2789456730901', 'Quetzaltenango, Quetzaltenango', 'admin@gmail.com', 'Mario Rodríguez', crypt('admin123', gen_salt('bf')), TRUE, (SELECT id_role FROM roles WHERE name_role = 'ADMINISTRADOR')),
--MODERADORES
('1987543620302', 'Zona 4, Quetzaltenango', 'mod1@gmail.com', 'Moderador 1', crypt('mod123', gen_salt('bf')), TRUE, (SELECT id_role FROM roles WHERE name_role = 'MODERADOR')),
('2458967130403', 'Zona 3, Ciudad de Guatemala', 'mod2@gmail.com', 'Moderador 2', crypt('mod123', gen_salt('bf')), TRUE, (SELECT id_role FROM roles WHERE name_role = 'MODERADOR')),
('3124576890504', 'Zona 10, Jutiapa', 'mod3@gmail.com', 'Moderador 3', crypt('mod123', gen_salt('bf')), TRUE, (SELECT id_role FROM roles WHERE name_role = 'MODERADOR')),
('1894765320605', 'Zona 5, San Marcos', 'mod4@gmail.com', 'Moderador 4', crypt('mod123', gen_salt('bf')), TRUE, (SELECT id_role FROM roles WHERE name_role = 'MODERADOR')),
('2056847910706', 'Zona 6, Petén', 'mod5@gmail.com', 'Moderador 5', crypt('mod123', gen_salt('bf')), TRUE, (SELECT id_role FROM roles WHERE name_role = 'MODERADOR')),
--LOGÍSTICA
('2547896310807', 'Zona 1, Quetzaltenango', 'log1@gmail.com', 'Logística 1', crypt('log123', gen_salt('bf')), TRUE, (SELECT id_role FROM roles WHERE name_role = 'LOGÍSTICA')),
('2986457310908', 'Zona 8, Quetzaltenango', 'log2@gmail.com', 'Logística 2', crypt('log123', gen_salt('bf')), TRUE, (SELECT id_role FROM roles WHERE name_role = 'LOGÍSTICA')),
('3158792461009', 'Zona 9, Quetzaltenango', 'log3@gmail.com', 'Logística 3', crypt('log123', gen_salt('bf')), TRUE, (SELECT id_role FROM roles WHERE name_role = 'LOGÍSTICA')),
--USUARIOS COMUNES
('2879654311101', 'Zona 10, Ciudad de Guatemala', 'user1@gmail.com', 'Usuario 1', crypt('user123', gen_salt('bf')), TRUE, (SELECT id_role FROM roles WHERE name_role = 'USUARIO COMÚN')),
('3147852691202', 'Zona 11, Ciudad de Guatemala', 'user2@gmail.com', 'Usuario 2', crypt('user123', gen_salt('bf')), TRUE, (SELECT id_role FROM roles WHERE name_role = 'USUARIO COMÚN')),
('2689457311303', 'Zona 12, Ciudad de Guatemala', 'user3@gmail.com', 'Usuario 3', crypt('user123', gen_salt('bf')), TRUE, (SELECT id_role FROM roles WHERE name_role = 'USUARIO COMÚN')),
('2896541731404', 'Zona 13, Ciudad de Guatemala', 'user4@gmail.com', 'Usuario 4', crypt('user123', gen_salt('bf')), TRUE, (SELECT id_role FROM roles WHERE name_role = 'USUARIO COMÚN')),
('3125798641505', 'Zona 14, Ciudad de Guatemala', 'user5@gmail.com', 'Usuario 5', crypt('user123', gen_salt('bf')), TRUE, (SELECT id_role FROM roles WHERE name_role = 'USUARIO COMÚN')),
('2458796311606', 'Zona 15, Ciudad de Guatemala', 'user6@gmail.com', 'Usuario 6', crypt('user123', gen_salt('bf')), TRUE, (SELECT id_role FROM roles WHERE name_role = 'USUARIO COMÚN')),
('1987645321707', 'Zona 16, Ciudad de Guatemala', 'user7@gmail.com', 'Usuario 7', crypt('user123', gen_salt('bf')), TRUE, (SELECT id_role FROM roles WHERE name_role = 'USUARIO COMÚN')),
('2765894311808', 'Zona 17, Ciudad de Guatemala', 'user8@gmail.com', 'Usuario 8', crypt('user123', gen_salt('bf')), TRUE, (SELECT id_role FROM roles WHERE name_role = 'USUARIO COMÚN')),
('3098746151909', 'Zona 18, Ciudad de Guatemala', 'user9@gmail.com', 'Usuario 9', crypt('user123', gen_salt('bf')), TRUE, (SELECT id_role FROM roles WHERE name_role = 'USUARIO COMÚN')),
('2549871362001', 'Zona 19, Ciudad de Guatemala', 'user10@gmail.com', 'Usuario 10', crypt('user123', gen_salt('bf')), TRUE, (SELECT id_role FROM roles WHERE name_role = 'USUARIO COMÚN'));

-- Productos
INSERT INTO products (product_name, description, image, price, stock, condition, id_category, user_dpi, created_at, updated_at) VALUES
-- Usuario 1
('Laptop HP Pavilion', 'Laptop HP 15” con 8GB RAM y SSD de 512GB.', 'hp_pavilion.jpg', 5500.00, 5, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Tecnología'), '2879654311101', NOW(), NOW()),
('Mouse Logitech M170', 'Mouse inalámbrico ergonómico.', 'mouse_logitech.jpg', 150.00, 20, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Hogar'), '2879654311101', NOW(), NOW()),
('Teclado Mecánico Redragon', 'Teclado retroiluminado RGB.', 'teclado_redragon.jpg', 375.00, 10, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Hogar'), '2879654311101', NOW(), NOW()),
('Monitor Samsung 24”', 'Pantalla LED Full HD.', 'monitor_samsung.jpg', 1250.00, 7, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Tecnología'), '2879654311101', NOW(), NOW()),
('Memoria USB 64GB', 'Memoria Kingston DataTraveler 3.0.', 'usb_64gb.jpg', 85.00, 25, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Académico'), '2879654311101', NOW(), NOW()),
('Silla Gamer Cougar', 'Silla ergonómica color negro y naranja.', 'silla_gamer.jpg', 1800.00, 4, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Personal'), '2879654311101', NOW(), NOW()),
('Router TP-Link', 'Router WiFi 5 doble banda.', 'router_tplink.jpg', 425.00, 8, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Decoración'), '2879654311101', NOW(), NOW()),
('Audífonos Sony WH-CH520', 'Audífonos inalámbricos Bluetooth.', 'audifonos_sony.jpg', 750.00, 6, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Hogar'), '2879654311101', NOW(), NOW()),
('Micrófono Condensador Fifine', 'Micrófono ideal para streaming.', 'microfono_fifine.jpg', 600.00, 9, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Hogar'), '2879654311101', NOW(), NOW()),
('Base para Laptop', 'Soporte ajustable de aluminio.', 'base_laptop.jpg', 220.00, 12, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Personal'), '2879654311101', NOW(), NOW()),

-- Usuario 2
('Celular Samsung A34', 'Teléfono Android 5G 128GB.', 'samsung_a34.jpg', 2500.00, 10, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Tecnología'), '3147852691202', NOW(), NOW()),
('Funda protectora', 'Funda transparente de silicona.', 'funda_silicon.jpg', 75.00, 50, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Hogar'), '3147852691202', NOW(), NOW()),
('Cargador rápido USB-C', 'Cargador de 25W original.', 'cargador_usb.jpg', 190.00, 20, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Hogar'), '3147852691202', NOW(), NOW()),
('Cable HDMI 2m', 'Cable de alta velocidad 4K.', 'cable_hdmi.jpg', 90.00, 30, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Hogar'), '3147852691202', NOW(), NOW()),
('Powerbank Xiaomi 10000mAh', 'Batería portátil compacta.', 'powerbank_xiaomi.jpg', 275.00, 15, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Académico'), '3147852691202', NOW(), NOW()),
('Smartwatch Amazfit', 'Reloj inteligente con sensor cardíaco.', 'smartwatch_amazfit.jpg', 950.00, 8, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Académico'), '3147852691202', NOW(), NOW()),
('Parlante JBL Go 3', 'Mini parlante Bluetooth resistente al agua.', 'parlante_jbl.jpg', 500.00, 12, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Personal'), '3147852691202', NOW(), NOW()),
('Audífonos con cable', 'Audífonos clásicos con jack 3.5mm.', 'audifonos_cable.jpg', 60.00, 40, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Personal'), '3147852691202', NOW(), NOW()),
('Tablet Lenovo Tab M10', 'Pantalla 10.1” Android 13.', 'tablet_lenovo.jpg', 1800.00, 5, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Tecnología'), '3147852691202', NOW(), NOW()),
('Protector de pantalla', 'Vidrio templado 9H.', 'vidrio_templado.jpg', 40.00, 60, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Hogar'), '3147852691202', NOW(), NOW()),

-- Usuario 3
('Impresora Epson EcoTank', 'Impresora multifuncional WiFi.', 'impresora_epson.jpg', 2100.00, 6, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Tecnología'), '2689457311303', NOW(), NOW()),
('Tinta Epson Negra', 'Botella de tinta original.', 'tinta_negra.jpg', 120.00, 30, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Hogar'), '2689457311303', NOW(), NOW()),
('Tinta Epson Color', 'Set de tintas CMY.', 'tinta_color.jpg', 320.00, 25, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Hogar'), '2689457311303', NOW(), NOW()),
('Resma papel carta', 'Papel blanco de 500 hojas.', 'papel_carta.jpg', 80.00, 50, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Académico'), '2689457311303', NOW(), NOW()),
('Engrapadora metálica', 'Engrapadora profesional de oficina.', 'engrapadora.jpg', 110.00, 20, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Académico'), '2689457311303', NOW(), NOW()),
('Cinta adhesiva 3M', 'Cinta transparente resistente.', 'cinta_adhesiva.jpg', 35.00, 40, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Académico'), '2689457311303', NOW(), NOW()),
('Mousepad ergonómico', 'Con soporte de gel.', 'mousepad.jpg', 95.00, 18, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Académico'), '2689457311303', NOW(), NOW()),
('Calculadora Casio', 'Calculadora científica FX-82MS.', 'calculadora.jpg', 225.00, 15, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Académico'), '2689457311303', NOW(), NOW()),
('Tijeras de oficina', 'Tijeras de acero inoxidable.', 'tijeras.jpg', 55.00, 25, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Académico'), '2689457311303', NOW(), NOW()),
('Archivador metálico', 'Archivador 4 gavetas.', 'archivador.jpg', 950.00, 3, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Académico'), '2689457311303', NOW(), NOW()),

-- Usuario 4
('Set de ollas Tramontina', 'Juego de ollas de acero inoxidable.', 'ollas_tramontina.jpg', 850.00, 5, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Hogar'), '2896541731404', NOW(), NOW()),
('Batidora Oster', 'Batidora de mano con 5 velocidades.', 'batidora_oster.jpg', 390.00, 8, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Hogar'), '2896541731404', NOW(), NOW()),
('Plancha de vapor Philips', 'Plancha con sistema anti goteo.', 'plancha_philips.jpg', 275.00, 10, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Hogar'), '2896541731404', NOW(), NOW()),
('Cafetera Black+Decker', 'Cafetera 12 tazas con filtro permanente.', 'cafetera_bd.jpg', 430.00, 6, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Hogar'), '2896541731404', NOW(), NOW()),
('Ventilador de torre', 'Ventilador con control remoto.', 'ventilador_torre.jpg', 680.00, 4, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Hogar'), '2896541731404', NOW(), NOW()),
('Horno eléctrico Hamilton Beach', 'Horno de 45L multifunción.', 'horno_hb.jpg', 950.00, 3, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Hogar'), '2896541731404', NOW(), NOW()),
('Licuadora Oster 12 velocidades', 'Motor potente y vaso de vidrio.', 'licuadora_oster.jpg', 525.00, 5, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Hogar'), '2896541731404', NOW(), NOW()),
('Refrigeradora LG', 'Refrigeradora de 11 pies cúbicos.', 'refrigeradora_lg.jpg', 3250.00, 2, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Hogar'), '2896541731404', NOW(), NOW()),
('Tostadora Hamilton Beach', 'Tostadora doble ranura extra ancha.', 'tostadora_hb.jpg', 320.00, 10, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Hogar'), '2896541731404', NOW(), NOW()),
('Freidora de aire', 'Freidora sin aceite 4L.', 'freidora_aire.jpg', 650.00, 7, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Hogar'), '2896541731404', NOW(), NOW()),

-- Usuario 5
('Libro: Cien años de soledad', 'Obra de Gabriel García Márquez.', 'cien_anos.jpg', 120.00, 15, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Académico'), '3125798641505', NOW(), NOW()),
('Libro: Don Quijote de la Mancha', 'Edición especial con ilustraciones.', 'don_quijote.jpg', 200.00, 8, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Académico'), '3125798641505', NOW(), NOW()),
('Libro: El principito', 'Edición de lujo ilustrada.', 'el_principito.jpg', 150.00, 12, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Académico'), '3125798641505', NOW(), NOW()),
('Cuaderno universitario', 'Cuaderno de 200 hojas rayado.', 'cuaderno_uni.jpg', 45.00, 50, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Académico'), '3125798641505', NOW(), NOW()),
('Bolígrafos Pilot', 'Set de 10 bolígrafos azul.', 'boligrafos_pilot.jpg', 60.00, 30, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Académico'), '3125798641505', NOW(), NOW()),
('Mochila Jansport', 'Mochila resistente con compartimiento para laptop.', 'mochila_jansport.jpg', 325.00, 10, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Académico'), '3125798641505', NOW(), NOW()),
('Calculadora científica Casio', 'Modelo FX-991EX.', 'calculadora_fx991ex.jpg', 350.00, 7, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Académico'), '3125798641505', NOW(), NOW()),
('Regla metálica 30cm', 'Regla de acero inoxidable.', 'regla_metalica.jpg', 25.00, 40, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Académico'), '3125798641505', NOW(), NOW()),
('Portafolio ejecutivo', 'Maletín de cuero sintético.', 'portafolio.jpg', 450.00, 5, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Académico'), '3125798641505', NOW(), NOW()),
('Agenda 2025', 'Agenda de tapa dura con calendario.', 'agenda2025.jpg', 90.00, 25, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Académico'), '3125798641505', NOW(), NOW()),

-- Usuario 6
('Silla ergonómica', 'Silla de oficina ajustable.', 'silla_ergonomica.jpg', 950.00, 4, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Personal'), '2458796311606', NOW(), NOW()),
('Escritorio moderno', 'Escritorio de madera con espacio para PC.', 'escritorio_moderno.jpg', 1250.00, 3, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Personal'), '2458796311606', NOW(), NOW()),
('Lámpara LED de escritorio', 'Luz blanca regulable.', 'lampara_led.jpg', 275.00, 12, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Personal'), '2458796311606', NOW(), NOW()),
('Cojín lumbar', 'Cojín ergonómico para espalda baja.', 'cojin_lumbar.jpg', 160.00, 10, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Personal'), '2458796311606', NOW(), NOW()),
('Organizador de cables', 'Set de clips para cables.', 'organizador_cables.jpg', 75.00, 25, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Personal'), '2458796311606', NOW(), NOW()),
('Tapete para silla', 'Protector de piso transparente.', 'tapete_silla.jpg', 220.00, 8, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Personal'), '2458796311606', NOW(), NOW()),
('Soporte de monitor', 'Soporte ajustable de aluminio.', 'soporte_monitor.jpg', 300.00, 7, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Personal'), '2458796311606', NOW(), NOW()),
('Estante organizador', 'Estante metálico de 4 niveles.', 'estante_metalico.jpg', 480.00, 6, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Personal'), '2458796311606', NOW(), NOW()),
('Pizarra blanca 90x60', 'Incluye marcadores y borrador.', 'pizarra_blanca.jpg', 275.00, 5, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Personal'), '2458796311606', NOW(), NOW()),
('Cesto de basura metálico', 'Cesto negro con diseño moderno.', 'cesto_basura.jpg', 85.00, 20, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Personal'), '2458796311606', NOW(), NOW()),

-- Usuario 7
('Taladro inalámbrico Bosch', 'Taladro 12V con batería extra.', 'taladro_bosch.jpg', 980.00, 5, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Otro'), '1987645321707', NOW(), NOW()),
('Caja de herramientas', 'Caja metálica de 50 piezas.', 'caja_herramientas.jpg', 450.00, 7, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Otro'), '1987645321707', NOW(), NOW()),
('Destornilladores Stanley', 'Set de 10 piezas.', 'destornilladores.jpg', 250.00, 15, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Otro'), '1987645321707', NOW(), NOW()),
('Martillo de carpintero', 'Cabeza de acero templado.', 'martillo.jpg', 125.00, 20, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Otro'), '1987645321707', NOW(), NOW()),
('Sierra eléctrica', 'Sierra circular 7 1/4”.', 'sierra_circular.jpg', 1150.00, 3, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Otro'), '1987645321707', NOW(), NOW()),
('Nivel láser', 'Nivel de precisión con trípode.', 'nivel_laser.jpg', 875.00, 2, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Otro'), '1987645321707', NOW(), NOW()),
('Llave inglesa', 'Llave ajustable 12”.', 'llave_inglesa.jpg', 95.00, 30, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Otro'), '1987645321707', NOW(), NOW()),
('Guantes de trabajo', 'Guantes resistentes de cuero.', 'guantes_trabajo.jpg', 70.00, 25, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Otro'), '1987645321707', NOW(), NOW()),
('Escalera plegable', 'Escalera de aluminio 3m.', 'escalera_aluminio.jpg', 650.00, 4, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Otro'), '1987645321707', NOW(), NOW()),
('Cinta métrica 5m', 'Cinta de medición profesional.', 'cinta_metrica.jpg', 60.00, 40, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Otro'), '1987645321707', NOW(), NOW()),

-- Usuario 8
('Collar de plata', 'Collar con dije de corazón.', 'collar_plata.jpg', 450.00, 6, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Personal'), '2765894311808', NOW(), NOW()),
('Perfume Dior Sauvage', 'Perfume original 100ml.', 'perfume_dior.jpg', 950.00, 4, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Personal'), '2765894311808', NOW(), NOW()),
('Bolso de cuero', 'Bolso artesanal color café.', 'bolso_cuero.jpg', 650.00, 5, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Personal'), '2765894311808', NOW(), NOW()),
('Lentes de sol Ray-Ban', 'Lentes con protección UV400.', 'lentes_rayban.jpg', 1100.00, 3, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Personal'), '2765894311808', NOW(), NOW()),
('Reloj Casio Vintage', 'Reloj dorado digital.', 'reloj_casio.jpg', 375.00, 8, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Personal'), '2765894311808', NOW(), NOW()),
('Chaqueta de mezclilla', 'Talla M unisex.', 'chaqueta_mezclilla.jpg', 420.00, 6, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Personal'), '2765894311808', NOW(), NOW()),
('Zapatos Adidas', 'Talla 39, color blanco.', 'zapatos_adidas.jpg', 680.00, 7, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Personal'), '2765894311808', NOW(), NOW()),
('Pulsera artesanal', 'Hecha con hilo trenzado.', 'pulsera_artesanal.jpg', 65.00, 20, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Personal'), '2765894311808', NOW(), NOW()),
('Sombrero Panamá', 'Sombrero original tejido.', 'sombrero_panama.jpg', 275.00, 10, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Personal'), '2765894311808', NOW(), NOW()),
('Bufanda tejida', 'Bufanda de lana color gris.', 'bufanda_lana.jpg', 120.00, 15, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Personal'), '2765894311808', NOW(), NOW()),

-- Usuario 9
('Planta suculenta', 'Maceta pequeña decorativa.', 'planta_suculenta.jpg', 95.00, 20, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Decoración'), '3098746151909', NOW(), NOW()),
('Cuadro abstracto', 'Pintura moderna en lienzo 50x70cm.', 'cuadro_abstracto.jpg', 750.00, 3, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Decoración'), '3098746151909', NOW(), NOW()),
('Espejo redondo', 'Marco de madera natural.', 'espejo_redondo.jpg', 420.00, 4, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Decoración'), '3098746151909', NOW(), NOW()),
('Lámpara colgante', 'Estilo industrial con bombillo LED.', 'lampara_colgante.jpg', 550.00, 5, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Decoración'), '3098746151909', NOW(), NOW()),
('Reloj de pared vintage', 'Reloj decorativo de metal.', 'reloj_pared.jpg', 375.00, 6, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Decoración'), '3098746151909', NOW(), NOW()),
('Cojines decorativos', 'Set de 3 cojines estampados.', 'cojines.jpg', 290.00, 10, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Decoración'), '3098746151909', NOW(), NOW()),
('Candelabros metálicos', 'Set de 2 candelabros dorados.', 'candelabros.jpg', 250.00, 8, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Decoración'), '3098746151909', NOW(), NOW()),
('Tapete étnico', 'Tapete de 1.5m x 1m.', 'tapete_etnico.jpg', 480.00, 3, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Decoración'), '3098746151909', NOW(), NOW()),
('Florero de cerámica', 'Florero blanco decorativo.', 'florero_ceramica.jpg', 150.00, 12, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Decoración'), '3098746151909', NOW(), NOW()),
('Cuadro con frase motivacional', 'Marco negro 30x40cm.', 'cuadro_frase.jpg', 180.00, 9, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Decoración'), '3098746151909', NOW(), NOW()),

-- Usuario 10
('Balón de fútbol Adidas', 'Balón oficial tamaño 5.', 'balon_adidas.jpg', 350.00, 12, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Otro'), '2549871362001', NOW(), NOW()),
('Raqueta de tenis Wilson', 'Raqueta ligera de grafito.', 'raqueta_tenis.jpg', 890.00, 5, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Otro'), '2549871362001', NOW(), NOW()),
('Guantes de portero', 'Guantes talla M con agarre Pro.', 'guantes_portero.jpg', 425.00, 8, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Otro'), '2549871362001', NOW(), NOW()),
('Camisola del Real Madrid', 'Original temporada 2024/2025.', 'camisola_rm.jpg', 950.00, 6, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Otro'), '2549871362001', NOW(), NOW()),
('Pesas ajustables', 'Set de 20kg con maletín.', 'pesas_ajustables.jpg', 1250.00, 4, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Otro'), '2549871362001', NOW(), NOW()),
('Colchoneta de yoga', 'Antideslizante, 6mm.', 'colchoneta_yoga.jpg', 225.00, 15, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Otro'), '2549871362001', NOW(), NOW()),
('Casco de ciclismo', 'Certificado y ventilado.', 'casco_ciclismo.jpg', 480.00, 10, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Otro'), '2549871362001', NOW(), NOW()),
('Botella deportiva', 'Botella de acero inoxidable 750ml.', 'botella_deportiva.jpg', 150.00, 25, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Otro'), '2549871362001', NOW(), NOW()),
('Toalla de microfibra', 'Toalla deportiva absorbente.', 'toalla_microfibra.jpg', 95.00, 20, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Otro'), '2549871362001', NOW(), NOW()),
('Cuerda para saltar', 'Cuerda ajustable de velocidad.', 'cuerda_saltar.jpg', 75.00, 30, TRUE, (SELECT id_category FROM categories WHERE category_name = 'Otro'), '2549871362001', NOW(), NOW());
