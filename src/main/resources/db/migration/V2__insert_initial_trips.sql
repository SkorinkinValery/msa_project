INSERT INTO trips (
    title,
    country,
    city,
    description,
    travel_date,
    created_at,
    updated_at
) VALUES
    (
        'Выходные в Гродно',
        'Беларусь',
        'Гродно',
        'Старый город и прогулка у замков',
        '2026-05-16',
        CURRENT_TIMESTAMP(6),
        CURRENT_TIMESTAMP(6)
    ),
    (
        'Летний отпуск',
        'Италия',
        'Рим',
        'Колизей и прогулка по центру',
        '2026-07-15',
        CURRENT_TIMESTAMP(6),
        CURRENT_TIMESTAMP(6)
    );
