CREATE TABLE IF NOT EXISTS menu_items (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    restaurant_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(2000),
    price NUMERIC(10,2) NOT NULL,
    currency VARCHAR(10) NOT NULL,

    created_at TIMESTAMPTZ DEFAULT now(),
    updated_at TIMESTAMPTZ DEFAULT now(),

    CONSTRAINT fk_menu_items_restaurant
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id),

    CONSTRAINT chk_menu_price CHECK (price > 0),
    CONSTRAINT chk_menu_name CHECK (length(trim(name)) > 0),

    CONSTRAINT unique_menu_item UNIQUE (restaurant_id, name)
    );