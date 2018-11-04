SELECT a.id, a.address, a.city, a.postal_code AS post
FROM addresses a
FULL OUTER JOIN users u on a.id = u.address_id
WHERE u.address_id is null
