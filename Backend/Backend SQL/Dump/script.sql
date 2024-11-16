create table if not exists impiegato
(
    cf             varchar(16) not null,
    nome           varchar(20) not null,
    cognome        varchar(20) not null,
    datanascita    date        not null,
    merito         boolean,
    codicecon      varchar(10) not null,
    dataassunzione date        not null,
    eta            integer,
    categoria      varchar(20) not null,
    salario        numeric     not null,
    primary key (cf),
    unique (cf, codicecon)
);

create table if not exists promozione
(
    cf             varchar(16) not null,
    codicecon      varchar(10) not null,
    datapassaggio  date        not null,
    nuovacategoria varchar(20),
    primary key (cf, codicecon),
    foreign key (cf) references impiegato
        on delete cascade,
    constraint fk_codicecon
        foreign key (cf, codicecon) references impiegato (cf, codicecon)
            on update cascade on delete cascade
);

create table if not exists laboratorio
(
    nome        varchar(20) not null,
    respscie    varchar(16) not null,
    topic       varchar(50) not null,
    n_afferenti integer,
    primary key (nome),
    foreign key (respscie) references impiegato
        on update cascade on delete set null,
    constraint laboratorio_respscie_fkey1
        foreign key (respscie) references impiegato,
    constraint issenior
        check (resposenior(respscie))
);

create table if not exists utilizza
(
    cf      varchar(16) not null,
    nomelab varchar(20) not null,
    foreign key (cf) references impiegato
        on update cascade on delete cascade,
    foreign key (nomelab) references laboratorio
        on update cascade on delete cascade
);

create table if not exists progetto
(
    cup      varchar(15) not null,
    refscie  varchar(16) not null,
    respscie varchar(16) not null,
    nome     varchar(20) not null,
    budget   numeric,
    primary key (cup),
    unique(cup, nome),
    foreign key (refscie) references impiegato
        on update cascade on delete set null,
    foreign key (respscie) references impiegato
        on update cascade on delete set null,
    constraint issenior
        check (resposenior(refscie)),
    constraint isdirector
        check (refdir(respscie))
);

create table if not exists lavora
(
    lab1 varchar(20) not null,
    lab2 varchar(20),
    lab3 varchar(20),
    cup  varchar(15),
    unique (lab1, cup),
    foreign key (lab1) references laboratorio
        on update cascade on delete set null,
    foreign key (lab2) references laboratorio
        on update cascade on delete set null,
    foreign key (lab3) references laboratorio
        on update cascade on delete set null,
    foreign key (cup) references progetto,
    constraint lavora_cup_fkey1
        foreign key (cup) references progetto
            on update cascade on delete cascade,
    constraint lavora_check
        check (((lab1)::text <> (lab2)::text) AND ((lab1)::text <> (lab3)::text) AND ((lab2)::text <> (lab3)::text))
);

create or replace function refdir(cod_fisc character varying) returns boolean
    language plpgsql
as
$$
BEGIN 
    RETURN EXISTS(
        SELECT 1
        FROM Impiegato
        WHERE CF = cod_fisc AND Categoria = 'Dirigente'
    );
END;
$$;

create or replace function resposenior(codice_fiscale character varying) returns boolean
    language plpgsql
as
$$
BEGIN
    RETURN EXISTS (
        SELECT 1
        FROM Impiegato
        WHERE CF = codice_fiscale AND Categoria = 'Senior'
    );
END;
$$;


create or replace procedure inserisciimpiegato(IN cf_n character varying, IN nome_n character varying, IN cognome_n character varying, IN datanascita_n date, IN merito_n boolean, IN codicecon_n character varying, IN dataassunzione_n date, IN categoria_n character varying, IN salario_n numeric)
    language plpgsql
as
$$
DECLARE
    eta_calcolata INTEGER;
BEGIN
    eta_calcolata := EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM dataNascita_n);

    IF eta_calcolata <= 18 THEN
        RAISE EXCEPTION 'L''impiegato deve avere più di 18 anni';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM Impiegato
        WHERE CF = cf_n AND CodiceCon = codiceCon_n
    ) THEN
        RAISE EXCEPTION 'La coppia (CF, CodiceCon) è già presente in Impiegato';
    END IF;

    IF salario_n < 1500 THEN
        RAISE EXCEPTION 'Il salario deve essere superiore a 1500';
    END IF;

    INSERT INTO Impiegato (CF, Nome, Cognome, DataNascita, Merito, CodiceCon, DataAssunzione, Eta, Categoria, Salario)
    VALUES (cf_n, nome_n, cognome_n, dataNascita_n, merito_n, codiceCon_n, dataAssunzione_n, eta_calcolata, categoria_n, salario_n);
END;
$$;

create or replace procedure inserisciutilizza(IN cf_n character varying, IN nomelab_n character varying)
    language plpgsql
as
$$
BEGIN
    -- Verifico che il CF inserito non è già stato inserito
    IF EXISTS (
        SELECT 1
        FROM Utilizza
        WHERE CF = CF_n AND NomeLab <> NomeLab_n
    ) THEN
        RAISE EXCEPTION 'Lo stesso CF è già associato a un altro Laboratorio';
    END IF;

    INSERT INTO Utilizza (CF, NomeLab)
    VALUES (CF_n, NomeLab_n);
END;
$$;

create or replace procedure inserisciprogetto(IN cup_n character varying, IN refscie_n character varying, IN respscie_n character varying, IN nome_n character varying, IN budget_n numeric)
    language plpgsql
as
$$
BEGIN
    IF NOT respoSenior(RefScie_n) THEN
        RAISE EXCEPTION 'Il referente scientifico deve essere di categoria senior';
    END IF;

    IF NOT refDir(RespScie_n) THEN
        RAISE EXCEPTION 'Il responsabile scientifico deve essere un dirigente';
    END IF;

    INSERT INTO Progetto (CUP, RefScie, RespScie, Nome, Budget)
    VALUES (CUP_n, RefScie_n, RespScie_n, Nome_n, Budget_n);
END;
$$;

create or replace procedure rimuovi_impiegato(IN cf_impiegato character varying)
    language plpgsql
as
$$
BEGIN
    -- Verifica se l'impiegato è un responsabile o referente scientifico
    IF EXISTS (SELECT 1 FROM Laboratorio WHERE RespScie = cf_impiegato) THEN
        UPDATE Laboratorio
        SET RespScie = (SELECT CF FROM Impiegato WHERE Categoria = 'Dipendente senior' LIMIT 1)
        WHERE RespScie = cf_impiegato;
    END IF;

    IF EXISTS (SELECT 1 FROM Progetto WHERE RespScie = cf_impiegato) THEN
        UPDATE Progetto
        SET RespScie = (SELECT CF FROM Impiegato WHERE Categoria = 'Dipendente senior' LIMIT 1)
        WHERE RespScie = cf_impiegato;
    END IF;

    DELETE FROM Impiegato WHERE CF = cf_impiegato;
END;
$$;

create or replace procedure rimuovi_laboratorio(IN nuovo_lab character varying)
    language plpgsql
as
$$
DECLARE
    Lab_el VARCHAR(20);
BEGIN
	-- Trova il nome del laboratorio che sta per essere rimosso
    SELECT NomeLab INTO Lab_el FROM Utilizza WHERE NomeLab = nuovo_Lab LIMIT 1;

    -- Verifica se il laboratorio da rimuovere è l'ultimo rimanente in un progetto
    IF (SELECT COUNT(*) FROM Lavora WHERE Lab1 = Lab_el OR Lab2 = Lab_el OR Lab3 = Lab_el) > 1 THEN
        -- Trova un laboratorio non impegnato in nessun progetto come rimpiazzo
        SELECT Nome INTO nuovo_Lab FROM Laboratorio
        WHERE Nome NOT IN (SELECT Lab1 FROM Lavora UNION SELECT Lab2 FROM Lavora UNION SELECT Lab3 FROM Lavora)
        LIMIT 1;
        
        -- Aggiorna il laboratorio che sta per essere rimosso con il rimpiazzo
        IF (SELECT Lab1 FROM Lavora WHERE Lab1 = Lab_el) IS NOT NULL THEN
            UPDATE Lavora SET Lab1 = nuovo_Lab WHERE Lab1 = Lab_el;
        END IF;

        IF (SELECT Lab2 FROM Lavora WHERE Lab2 = Lab_el) IS NOT NULL THEN
            UPDATE Lavora SET Lab2 = nuovo_Lab WHERE Lab2 = Lab_el;
        END IF;

        IF (SELECT Lab3 FROM Lavora WHERE Lab3 = Lab_el) IS NOT NULL THEN
            UPDATE Lavora SET Lab3 = nuovo_Lab WHERE Lab3 = Lab_el;
        END IF;
    END IF;
	
    DELETE FROM Laboratorio WHERE Nome = nuovo_Lab;
END;
$$;

create or replace procedure rimuovi_utilizza(IN cf_impiegato character varying, IN nome_lab character varying)
    language plpgsql
as
$$
BEGIN
    DELETE FROM Utilizza
    WHERE CF = cf_impiegato AND NomeLab = nome_lab;

    UPDATE Laboratorio
    SET N_Afferenti = N_Afferenti - 1
    WHERE Nome = nome_lab;
END;
$$;

create or replace procedure rimuovi_progetto(IN cup character varying)
    language plpgsql
as
$$
BEGIN
    DELETE FROM Progetto WHERE CUP = cup;
END;
$$;

create or replace procedure rimuovi_lavora(IN cup character varying)
    language plpgsql
as
$$
BEGIN
    DELETE FROM Lavora WHERE CUP = cup;
END;
$$;

create or replace procedure rimuovi_promozione(cf_impiegato character varying, codice_contratto character varying)
as
$$
BEGIN
    DELETE FROM Promozione
    WHERE CF = cf_impiegato AND CodiceCon = codice_contratto;
END;
$$;

create or replace function passaggio_ruolo() returns trigger
    language plpgsql
as
$$
BEGIN
    -- Verifica se l'impiegato è un dirigente in base al merito
    IF NEW.Merito THEN
        NEW.Categoria = 'Dirigente';
    END IF;

    -- Se l'impiegato non è un dirigente, verifica l'anzianità e aggiorna la categoria di conseguenza
    IF NOT NEW.Merito THEN
        IF NEW.Eta < 3 THEN
            NEW.Categoria = 'Dipendente junior';
        ELSIF NEW.Eta >= 3 AND NEW.Eta < 7 THEN
            NEW.Categoria = 'Dipendente middle';
        ELSIF NEW.Eta >= 7 THEN
            NEW.Categoria = 'Dipendente senior';
        END IF;
    END IF;

    RETURN NEW;
END;
$$;

create or replace function limite_laboratori() returns trigger
    language plpgsql
as
$$
BEGIN
    -- Conta il numero attuale di laboratori associati al progetto
    IF (SELECT COUNT(*) FROM Lavora WHERE CUP = OLD.CUP) > 3 THEN
        RAISE EXCEPTION 'Un progetto può avere al massimo 3 laboratori';
    END IF;

    RETURN OLD;
END;
$$;

create or replace function dirigente(cod_fisc character varying) returns boolean
    language plpgsql
as
$$
BEGIN 
    IF EXISTS(
        SELECT 1
        FROM Impiegato
        WHERE CF = cod_fisc AND Categoria <> 'Dirigente'
    ) THEN 
		UPDATE Progetto
		SET RespScie = (SELECT CF FROM Impiegato WHERE Categoria = 'Dirigente')
		WHERE RespScie IS NULL;
		RETURN TRUE;
	END IF;
END;
$$;

create or replace function senior(codice_fiscale character varying) returns boolean
    language plpgsql
as
$$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM Impiegato
        WHERE CF = codice_fiscale AND Categoria <> 'Senior'
    ) THEN
	UPDATE Progetto
        SET RefScie = (SELECT CF FROM Impiegato WHERE Categoria = 'Senior' AND CF NOT IN (SELECT RefScie FROM Progetto) LIMIT 1)
        WHERE RefScie IS NULL;

        UPDATE Laboratorio
        SET RespScie = (SELECT CF FROM Impiegato WHERE Categoria = 'Senior' AND CF NOT IN (SELECT RespScie FROM Laboratorio) LIMIT 1)
        WHERE RespScie IS NULL;
		RETURN TRUE;
	END IF;
END;
$$;

create or replace procedure inseriscipromozione(IN cf_n character varying, IN datapassaggio_n date, IN codicecon_n character varying, IN categoria_n character varying)
    language plpgsql
as
$$
BEGIN
    -- Verifica se l'impiegato esiste
    IF NOT EXISTS (
        SELECT 1
        FROM Impiegato
        WHERE CF = CF_n AND CodiceCon = CodiceCon_n
    ) THEN
        RAISE EXCEPTION 'La coppia (CF, CodiceCon) non corrisponde a una coppia già presente in Impiegato';
    END IF;

    -- Verifica se la data di passaggio è successiva alla data di assunzione
    IF NOT EXISTS (
        SELECT 1
        FROM Impiegato
        WHERE CF = CF_n AND DataAssunzione <= DataPassaggio_n
    ) THEN
        RAISE EXCEPTION 'La data di passaggio deve avvenire dopo l''assunzione dell''impiegato';
    END IF;

    -- Determina la nuova categoria in base all'anzianità e al merito
    SELECT
        CASE
            WHEN EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM DataAssunzione) > 7 THEN 'Senior'
            WHEN EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM DataAssunzione) > 3 THEN 'Middle'
            ELSE 'Junior'
        END
    INTO Categoria_n
    FROM Impiegato
    WHERE CF = CF_n AND CodiceCon = CodiceCon_n;

    -- Aggiorna la categoria se l'impiegato ha merito
    IF EXISTS (
        SELECT 1
        FROM Impiegato
        WHERE CF = CF_n AND CodiceCon = CodiceCon_n AND Merito = TRUE
    ) THEN
        Categoria_n := 'Dirigente';
    END IF;

    -- Inserisci la promozione
    INSERT INTO Promozione (CF, DataPassaggio, CodiceCon, NuovaCategoria)
    VALUES (CF_n, DataPassaggio_n, CodiceCon_n, Categoria_n);
END;
$$;

create or replace function aggiornan_afferenti() returns trigger
    language plpgsql
as
$$
BEGIN

    UPDATE Laboratorio
    SET N_Afferenti = N_Afferenti + 1
    WHERE Nome = NEW.NomeLab;

    RETURN NEW;
END;
$$;

create 
 ins_utilizza
    before insert
    on utilizza
    for each row
execute procedure aggiornan_afferenti();

create or replace procedure inseriscilaboratorio(IN nome_n character varying, IN respscie_n character varying, IN topic_n character varying, IN afferenti_n numeric)
    language plpgsql
as
$$
BEGIN

    INSERT INTO Laboratorio (Nome, RespScie, Topic, N_Afferenti)
    VALUES (Nome_n, RespScie_n, Topic_n, Afferenti_n);
END;
$$;


