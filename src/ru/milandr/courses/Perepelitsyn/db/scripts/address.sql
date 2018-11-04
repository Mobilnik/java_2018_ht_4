SELECT u.id, u.first_name, u.last_name, u.phone_number
FROM users u INNER JOIN addresses a on u.address_id = a.id WHERE a.address = 'P.O. Box 677, 8665 Ante Road'