-- Ensure DB-managed timestamps for party, same as enumeration
-- created_date: NOT NULL DEFAULT CURRENT_TIMESTAMP
-- updated_date: NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
ALTER TABLE party
    MODIFY created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    MODIFY updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

-----------------------------------------------------------------------------------------------------------
-- Developer (DEVELOPER)
INSERT INTO party (party_type, status, first_name, last_name, gender, org_name, from_date, thru_date)
SELECT 'PERSON', 'ACTIVE', 'developer', NULL, NULL, NULL, NOW(), NULL
    WHERE NOT EXISTS (SELECT 1 FROM party WHERE first_name = 'developer');

INSERT INTO party_roles (party_id, role)
SELECT p.party_id, 'DEVELOPER'
FROM party p
WHERE p.first_name = 'developer'
  AND NOT EXISTS (
    SELECT 1 FROM party_roles pr
    WHERE pr.party_id = p.party_id AND pr.role = 'DEVELOPER'
);

-----------------------------------------------------------------------------------------------------------
-- Ram (WORKER)
INSERT INTO party (party_type, status, first_name, last_name, gender, org_name, from_date, thru_date)
SELECT 'PERSON', 'ACTIVE', 'Ram', NULL, NULL, NULL, NOW(), NULL
    WHERE NOT EXISTS (SELECT 1 FROM party WHERE first_name = 'Ram');

INSERT INTO party_roles (party_id, role)
SELECT p.party_id, 'WORKER'
FROM party p
WHERE p.first_name = 'Ram'
  AND NOT EXISTS (
    SELECT 1 FROM party_roles pr
    WHERE pr.party_id = p.party_id AND pr.role = 'WORKER'
);

-----------------------------------------------------------------------------------------------------------
-- Shyam (WORKER)
INSERT INTO party (party_type, status, first_name, last_name, gender, org_name, from_date, thru_date)
SELECT 'PERSON', 'ACTIVE', 'Shyam', NULL, NULL, NULL, NOW(), NULL
    WHERE NOT EXISTS (SELECT 1 FROM party WHERE first_name = 'Shyam');

INSERT INTO party_roles (party_id, role)
SELECT p.party_id, 'WORKER'
FROM party p
WHERE p.first_name = 'Shyam'
  AND NOT EXISTS (
    SELECT 1 FROM party_roles pr
    WHERE pr.party_id = p.party_id AND pr.role = 'WORKER'
);

-----------------------------------------------------------------------------------------------------------
-- Raj (WORKER)
INSERT INTO party (party_type, status, first_name, last_name, gender, org_name, from_date, thru_date)
SELECT 'PERSON', 'ACTIVE', 'Raj', NULL, NULL, NULL, NOW(), NULL
    WHERE NOT EXISTS (SELECT 1 FROM party WHERE first_name = 'Raj');

INSERT INTO party_roles (party_id, role)
SELECT p.party_id, 'WORKER'
FROM party p
WHERE p.first_name = 'Raj'
  AND NOT EXISTS (
    SELECT 1 FROM party_roles pr
    WHERE pr.party_id = p.party_id AND pr.role = 'WORKER'
);

-----------------------------------------------------------------------------------------------------------
-- John (WORKER)
INSERT INTO party (party_type, status, first_name, last_name, gender, org_name, from_date, thru_date)
SELECT 'PERSON', 'ACTIVE', 'John', NULL, NULL, NULL, NOW(), NULL
    WHERE NOT EXISTS (SELECT 1 FROM party WHERE first_name = 'John');

INSERT INTO party_roles (party_id, role)
SELECT p.party_id, 'WORKER'
FROM party p
WHERE p.first_name = 'John'
  AND NOT EXISTS (
    SELECT 1 FROM party_roles pr
    WHERE pr.party_id = p.party_id AND pr.role = 'WORKER'
);
-----------------------------------------------------------------------------------------------------------
