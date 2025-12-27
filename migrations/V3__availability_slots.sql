CREATE TABLE IF NOT EXISTS availability_slots (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    restaurant_id uuid NOT NULL,
    slot_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    capacity INT NOT NULL,
    seats_available INT NOT NULL,

    created_at TIMESTAMPTZ DEFAULT now(),
    updated_at TIMESTAMPTZ DEFAULT now(),

    CONSTRAINT fk_availability_slots_restaurant
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id),

    CONSTRAINT chk_time_valid CHECK (start_time <> end_time),
    CONSTRAINT chk_capacity_valid CHECK (
        capacity > 0 AND
        seats_available >= 0 AND
        seats_available <= capacity
    ),

    CONSTRAINT unique_slot UNIQUE (restaurant_id, slot_date, start_time, end_time)
);
