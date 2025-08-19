-- Ensure DB-managed timestamps for user_login, same as party/enumeration
-- created_date: NOT NULL DEFAULT CURRENT_TIMESTAMP
-- updated_date: NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
ALTER TABLE user_login
    MODIFY created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    MODIFY updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

-----------------------------------------------------------------------------------------------------------
-- IMPORTANT: BCrypt-hash for password "12345"
-- Replace this with your actual BCrypt hash produced by your app's PasswordEncoder.
-- Example placeholder (do not use in production without verifying):
-- $2a$10$7a6K4A7fY9yFZq0PZpS5Te0l2D0mM2wH3qfKc2xqk3Gm9O9r8vZhi
-- Below is a commonly seen 10-round BCrypt hash for "12345" for SEEDING ONLY.
-- Verify the encoder strength matches your PasswordEncoder setup.
SET @pwd_hash = '$2a$10$N9qo8uLOickgx2ZMRZo5e.7iJ8QqL7xQf7xAq2k4bV0U0KqvF/8Ga';

-----------------------------------------------------------------------------------------------------------
-- Developer userLogin (user_login_id = 'developer')
-- Insert only if not exists; link to Party('developer'); set from_date; rely on DB for timestamps
INSERT INTO user_login (user_login_id, password, password_hint, party_id, from_date, thru_date)
SELECT 'developer', @pwd_hash, 'default hint', p.party_id, NOW(), NULL
FROM party p
WHERE p.first_name = 'developer'
  AND NOT EXISTS (SELECT 1 FROM user_login ul WHERE ul.user_login_id = 'developer')
    LIMIT 1;

-- Optional: grant elevated permissions to developer
INSERT INTO user_login_permissions (user_login_id, permission_id)
SELECT 'developer', 'CREATE_AND_UPDATE_ORDERS'
    WHERE NOT EXISTS (
  SELECT 1 FROM user_login_permissions up
  WHERE up.user_login_id = 'developer' AND up.permission_id = 'CREATE_AND_UPDATE_ORDERS'
);

INSERT INTO user_login_permissions (user_login_id, permission_id)
SELECT 'developer', 'CREATE_AND_UPDATE_CONSUMERS'
    WHERE NOT EXISTS (
  SELECT 1 FROM user_login_permissions up
  WHERE up.user_login_id = 'developer' AND up.permission_id = 'CREATE_AND_UPDATE_CONSUMERS'
);

INSERT INTO user_login_permissions (user_login_id, permission_id)
SELECT 'developer', 'VIEW_PERMISSIONS'
    WHERE NOT EXISTS (
  SELECT 1 FROM user_login_permissions up
  WHERE up.user_login_id = 'developer' AND up.permission_id = 'VIEW_PERMISSIONS'
);

INSERT INTO user_login_permissions (user_login_id, permission_id)
SELECT 'developer', 'CREATE_AND_UPDATE_PERMISSIONS'
    WHERE NOT EXISTS (
  SELECT 1 FROM user_login_permissions up
  WHERE up.user_login_id = 'developer' AND up.permission_id = 'CREATE_AND_UPDATE_PERMISSIONS'
);
-----------------------------------------------------------------------------------------------------------
-- Ram userLogin (user_login_id = 'ram')
INSERT INTO user_login (user_login_id, password, password_hint, party_id, from_date, thru_date)
SELECT 'ram', @pwd_hash, 'default hint', p.party_id, NOW(), NULL
FROM party p
WHERE p.first_name = 'Ram'
  AND NOT EXISTS (SELECT 1 FROM user_login ul WHERE ul.user_login_id = 'ram')
    LIMIT 1;

-----------------------------------------------------------------------------------------------------------
-- Shyam userLogin (user_login_id = 'shyam')
INSERT INTO user_login (user_login_id, password, password_hint, party_id, from_date, thru_date)
SELECT 'shyam', @pwd_hash, 'default hint', p.party_id, NOW(), NULL
FROM party p
WHERE p.first_name = 'Shyam'
  AND NOT EXISTS (SELECT 1 FROM user_login ul WHERE ul.user_login_id = 'shyam')
    LIMIT 1;

-----------------------------------------------------------------------------------------------------------
-- Raj userLogin (user_login_id = 'raj')
INSERT INTO user_login (user_login_id, password, password_hint, party_id, from_date, thru_date)
SELECT 'raj', @pwd_hash, 'default hint', p.party_id, NOW(), NULL
FROM party p
WHERE p.first_name = 'Raj'
  AND NOT EXISTS (SELECT 1 FROM user_login ul WHERE ul.user_login_id = 'raj')
    LIMIT 1;

-----------------------------------------------------------------------------------------------------------
-- John userLogin (user_login_id = 'john')
INSERT INTO user_login (user_login_id, password, password_hint, party_id, from_date, thru_date)
SELECT 'john', @pwd_hash, 'default hint', p.party_id, NOW(), NULL
FROM party p
WHERE p.first_name = 'John'
  AND NOT EXISTS (SELECT 1 FROM user_login ul WHERE ul.user_login_id = 'john')
    LIMIT 1;

-----------------------------------------------------------------------------------------------------------
-- Optionally, seed default LOGIN_PERMISSION links for each user in user_login_permissions
-- This assumes the enumeration table already has LOGIN_PERMISSION rows and uses snake_case PKs.
-- Each block only inserts if the (user_login_id, permission_id) pair does not already exist.

-- Example: grant VIEW_CONSUMERS to all five users
INSERT INTO user_login_permissions (user_login_id, permission_id)
SELECT 'developer', 'VIEW_CONSUMERS'
    WHERE NOT EXISTS (
  SELECT 1 FROM user_login_permissions up
  WHERE up.user_login_id = 'developer' AND up.permission_id = 'VIEW_CONSUMERS'
);

INSERT INTO user_login_permissions (user_login_id, permission_id)
SELECT 'ram', 'VIEW_CONSUMERS'
    WHERE NOT EXISTS (
  SELECT 1 FROM user_login_permissions up
  WHERE up.user_login_id = 'ram' AND up.permission_id = 'VIEW_CONSUMERS'
);

INSERT INTO user_login_permissions (user_login_id, permission_id)
SELECT 'shyam', 'VIEW_CONSUMERS'
    WHERE NOT EXISTS (
  SELECT 1 FROM user_login_permissions up
  WHERE up.user_login_id = 'shyam' AND up.permission_id = 'VIEW_CONSUMERS'
);

INSERT INTO user_login_permissions (user_login_id, permission_id)
SELECT 'raj', 'VIEW_CONSUMERS'
    WHERE NOT EXISTS (
  SELECT 1 FROM user_login_permissions up
  WHERE up.user_login_id = 'raj' AND up.permission_id = 'VIEW_CONSUMERS'
);

INSERT INTO user_login_permissions (user_login_id, permission_id)
SELECT 'john', 'VIEW_CONSUMERS'
    WHERE NOT EXISTS (
  SELECT 1 FROM user_login_permissions up
  WHERE up.user_login_id = 'john' AND up.permission_id = 'VIEW_CONSUMERS'
);
-----------------------------------------------------------------------------------------------------------