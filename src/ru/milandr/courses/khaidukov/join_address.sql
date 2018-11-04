SELECT u.id, u.first_name AS Name, u.last_name AS Surname, u.phone_number AS Phone
FROM users u
INNER JOIN addresses a on u.address_id = a.id
WHERE a.address = 'P.O. Box 677, 8665 Ante Road'