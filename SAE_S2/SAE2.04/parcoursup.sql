DROP TABLE IF EXISTS GroupeClassement;
DROP TABLE IF EXISTS StatsAcces;
DROP TABLE IF EXISTS StatsPropositionsTerminales;
DROP TABLE IF EXISTS StatsPropositionAdmissions;
DROP TABLE IF EXISTS StatsCandidatsClasses;
DROP TABLE IF EXISTS StatsCapaciteCandidats;
DROP TABLE IF EXISTS Formation;
DROP TABLE IF EXISTS Etablissement;
DROP TABLE IF EXISTS Departement;
DROP TABLE IF EXISTS Session;

\i init.sql

-- CREATION DES TABLSE

CREATE TABLE Session(
    n1 SMALLINT,
    CONSTRAINT pk_session PRIMARY KEY(n1)
);

CREATE TABLE Departement(
    n5 VARCHAR(3),
    n6 VARCHAR(25),
    n7 VARCHAR(29),

    CONSTRAINT pk_departement PRIMARY KEY(n5)
);

CREATE TABLE Etablissement(
    n3 CHAR(8),
    n4 TEXT,
    n2 VARCHAR(35),
    n5 VARCHAR(3),
    n8 VARCHAR(21),
    n9 VARCHAR(32),

    CONSTRAINT pk_etablissement PRIMARY KEY(n3),
    CONSTRAINT fk_departement FOREIGN KEY (n5) REFERENCES Departement(n5)
);

CREATE TABLE Formation(
    n110 INT,
    n3 CHAR(8),
    n10 TEXT,
    n11 VARCHAR(25),
    n12 VARCHAR(19),
    n13 TEXT,
    n14 VARCHAR(73),
    n15 TEXT,
    n16 TEXT,

    CONSTRAINT pk_formation PRIMARY KEY (n110),
    CONSTRAINT fk_etablissement FOREIGN KEY (n3) REFERENCES Etablissement(n3)
);

CREATE TABLE StatsCapaciteCandidats(
    n1 SMALLINT,
    n110 INT,

    n18 SMALLINT,
    n19 SMALLINT,
    n20 SMALLINT,
    n21 SMALLINT,
    n22 SMALLINT,
    n23 SMALLINT,
    n24 SMALLINT,
    n25 SMALLINT,
    n26 SMALLINT,
    n27 SMALLINT,
    n28 SMALLINT,
    n29 SMALLINT,
    n30 SMALLINT,
    n31 SMALLINT,
    n32 SMALLINT,
    n33 SMALLINT,
    n34 SMALLINT,

    CONSTRAINT pk_statsCapaciteCandidats PRIMARY KEY (n1, n110),
    CONSTRAINT fk_sCapCan_session FOREIGN KEY (n1) REFERENCES Session(n1),
    CONSTRAINT fk_sCapCan_formation FOREIGN KEY (n110) REFERENCES Formation(n110)
);


CREATE TABLE StatsCandidatsClasses(
    n1 SMALLINT,
    n110 INT,

    n35 SMALLINT,
    n36 SMALLINT,
    n37 SMALLINT,
    n38 SMALLINT,
    n39 SMALLINT,
    n40 SMALLINT,
    n41 SMALLINT,
    n42 SMALLINT,
    n43 SMALLINT,
    n44 SMALLINT,
    n45 SMALLINT,
    
    CONSTRAINT pk_statsCandidatsClasses PRIMARY KEY (n1, n110),
    CONSTRAINT fk_sCanCla_session FOREIGN KEY (n1) REFERENCES Session(n1),
    CONSTRAINT fk_sCanCla_formation FOREIGN KEY (n110) REFERENCES Formation(n110)
);

CREATE TABLE StatsPropositionAdmissions(
    n1 SMALLINT,
    n110 INT,

    n46 SMALLINT,
    n47 SMALLINT,
    n48 SMALLINT,
    n49 SMALLINT,
    n50 SMALLINT,
    n51 SMALLINT,
    n52 SMALLINT,
    n53 SMALLINT,
    n54 SMALLINT,
    n55 SMALLINT,
    n56 SMALLINT,
    n57 SMALLINT,
    n58 SMALLINT,
    n59 SMALLINT,
    n60 SMALLINT,
    n61 SMALLINT,
    n62 SMALLINT,
    n63 SMALLINT,
    n64 SMALLINT,
    n65 SMALLINT,
    n66 SMALLINT,
    n67 SMALLINT,
    n68 SMALLINT,
    n69 SMALLINT,
    n70 SMALLINT,
    n71 SMALLINT,
    n72 SMALLINT,
    n73 SMALLINT,

    CONSTRAINT pk_statsPropositionAdmissions PRIMARY KEY (n1, n110),
    CONSTRAINT fk_sProAdm_session FOREIGN KEY (n1) REFERENCES Session(n1),
    CONSTRAINT fk_sProAdm_formation FOREIGN KEY (n110) REFERENCES Formation(n110)
);


CREATE TABLE StatsPropositionsTerminales(
    n1 SMALLINT,
    n110 INT,

    n95 NUMERIC(5, 1),
    n96 NUMERIC(5, 1),
    n97 NUMERIC(5, 1),
    n98 NUMERIC(5, 1),
    n99 NUMERIC(5, 1),
    n100 NUMERIC(5, 1),
    n101 NUMERIC(5, 1),

    CONSTRAINT pk_statsPropositionsTerminales PRIMARY KEY (n1, n110),
    CONSTRAINT fk_sProTer_session FOREIGN KEY (n1) REFERENCES Session(n1),
    CONSTRAINT fk_sProTer_formation FOREIGN KEY (n110) REFERENCES Formation(n110)
);


CREATE TABLE StatsAcces(
    n1 SMALLINT,
    n110 INT,

    n113 SMALLINT,  
    n114 SMALLINT,
    n115 SMALLINT,
    n116 SMALLINT,

    CONSTRAINT pk_statsAcces PRIMARY KEY (n1, n110),
    CONSTRAINT fk_sAcc_session FOREIGN KEY (n1) REFERENCES Session(n1),
    CONSTRAINT fk_sAcc_formation FOREIGN KEY (n110) REFERENCES Formation(n110)
);

CREATE TABLE GroupeClassement(
    n1 SMALLINT,
    n110 INT,

    num_grp SMALLINT,
    regroupement VARCHAR(150),
    rang_dernier_appele INT,

    PRIMARY KEY (n1, n110, num_grp),
    FOREIGN KEY (n1) REFERENCES Session(n1),
    FOREIGN KEY (n110) REFERENCES Formation(n110)
);

-- IMPORT DES DONNEES 

INSERT INTO Session (n1)
SELECT DISTINCT n1 
FROM import;

INSERT INTO Departement (n5, n6, n7)
SELECT DISTINCT n5, n6, n7
FROM import;

INSERT INTO Etablissement (n3, n4, n2, n5, n8, n9)
SELECT DISTINCT n3, n4, n2, n5,n8, n9
FROM import;

INSERT INTO Formation (n110, n3, n10, n11, n12, n13, n14, n15, n16)
SELECT DISTINCT n110, n3, n10, n11, n12, n13, n14, n15, n16
FROM import;

INSERT INTO StatsCapaciteCandidats (n1, n110, n18, n19, n20, n21, n22, n23, n24, n25, n26, n27, n28, n29, n30, n31, n32, n33, n34)
SELECT n1, n110, n18, n19, n20, n21, n22, n23, n24, n25, n26, n27, n28, n29, n30, n31, n32, n33, n34
FROM import;

INSERT INTO StatsCandidatsClasses (n1, n110, n35, n36, n37, n38, n39, n40, n41, n42, n43, n44, n45)
SELECT n1, n110, n35, n36, n37, n38, n39, n40, n41, n42, n43, n44, n45
FROM import;

INSERT INTO StatsPropositionAdmissions (n1, n110, n46, n47, n48, n49, n50, n51, n52, n53, n54, n55, n56, n57, n58, n59, n60, n61, n62, n63, n64, n65, n66, n67, n68, n69, n70, n71, n72, n73)
SELECT n1, n110, n46, n47, n48, n49, n50, n51, n52, n53, n54, n55, n56, n57, n58, n59, n60, n61, n62, n63, n64, n65, n66, n67, n68, n69, n70, n71, n72, n73
FROM import;

INSERT INTO StatsPropositionsTerminales (n1, n110, n95, n96, n97, n98, n99, n100, n101)
SELECT n1, n110, n95, n96, n97, n98, n99, n100, n101
FROM import;

INSERT INTO StatsAcces (n1, n110, n113, n114, n115, n116)
SELECT n1, n110, n113, n114, n115, n116
FROM import;

INSERT INTO GroupeClassement (n1, n110, num_grp, regroupement, rang_dernier_appele)
SELECT n1, n110, 1, n102, n103 FROM import WHERE n102 IS NOT NULL AND n102 <> '';

INSERT INTO GroupeClassement (n1, n110, num_grp, regroupement, rang_dernier_appele)
SELECT n1, n110, 2, n104, n105 FROM import WHERE n104 IS NOT NULL AND n104 <> '';

INSERT INTO GroupeClassement (n1, n110, num_grp, regroupement, rang_dernier_appele)
SELECT n1, n110, 3, n106, n107 FROM import WHERE n106 IS NOT NULL AND n106 <> '';
