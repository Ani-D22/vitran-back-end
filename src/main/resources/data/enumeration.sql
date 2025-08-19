ALTER TABLE enumeration
    MODIFY created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    MODIFY updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

-----------------------------------------------------------------------------------------------------------
-- LOGIN_PERMISSION
INSERT IGNORE INTO enumeration (enum_id, enum_type_id, description) VALUES
  ('VIEW_ORDERS', 'LOGIN_PERMISSION', 'Can view orders'),
  ('CREATE_AND_UPDATE_ORDERS', 'LOGIN_PERMISSION', 'Can create and update orders'),
  ('VIEW_PERMISSIONS', 'LOGIN_PERMISSION', 'Can view permissions'),
  ('CREATE_AND_UPDATE_PERMISSIONS', 'LOGIN_PERMISSION', 'Can create and update permissions'),
  ('VIEW_CONSUMERS', 'LOGIN_PERMISSION', 'Can view consumers'),
  ('CREATE_AND_UPDATE_CONSUMERS', 'LOGIN_PERMISSION', 'Can create and update consumers'),
  ('VIEW_WORKERS', 'LOGIN_PERMISSION', 'Can view workers'),
  ('CREATE_AND_UPDATE_WORKERS', 'LOGIN_PERMISSION', 'Can create and update workers'),
  ('VIEW_ADMINS', 'LOGIN_PERMISSION', 'Can view admins'),
  ('CREATE_AND_UPDATE_ADMINS', 'LOGIN_PERMISSION', 'Can create and update admins');

-----------------------------------------------------------------------------------------------------------
-- PARTY_TYPE
INSERT IGNORE INTO enumeration (enum_id, enum_type_id, description) VALUES
  ('PERSON', 'PARTY_TYPE', 'Individual person'),
  ('ORGANIZATION', 'PARTY_TYPE', 'Organization or business');

-----------------------------------------------------------------------------------------------------------
-- PARTY_STATUS
INSERT IGNORE INTO enumeration (enum_id, enum_type_id, description) VALUES
  ('ACTIVE', 'PARTY_STATUS', 'Active status'),
  ('INACTIVE', 'PARTY_STATUS', 'Inactive status'),
  ('SUSPENDED', 'PARTY_STATUS', 'Suspended status');
-----------------------------------------------------------------------------------------------------------
