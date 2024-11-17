<<<<<<< HEAD
from sqlalchemy import Column, String, Integer, Boolean, Date, Numeric, ForeignKey
from sqlalchemy.orm import declarative_base

Base = declarative_base()

class Impiegato(Base):
    __tablename__ = "impiegato"
    
    cf = Column(String(16),primary_key = True)
    nome = Column(String(20), nullable=False)
    cognome = Column(String(20), nullable=False)
    datanascita = Column(Date, nullable=False)
    merito = Column(Boolean, default=False)
    codicecon = Column(String(10), nullable=False, unique=True)
    dataassunzione = Column(Date, nullable=False)
    eta = Column(Integer)
    categoria = Column(String(20), nullable=False)
    salario = Column(Numeric, nullable=False)
    
class Promozione(Base):
    __tablename__ = "promozione"

    cf = Column(String(16), ForeignKey("impiegato.cf"), primary_key=True)
    codicecon = Column(String(10), primary_key=True)
    datapassaggio = Column(Date, nullable=False)
    nuovacategoria = Column(String(20), nullable=True)

class Laboratorio(Base):
    __tablename__ = "laboratorio"

    nome = Column(String(20), primary_key=True)
    respscie = Column(String(16), ForeignKey("impiegato.cf"), nullable=False)
    topic = Column(String(50), nullable=False)
    n_afferenti = Column(Integer, nullable=True)

class Progetto(Base):
    __tablename__ = "progetto"

    cup = Column(String(15), primary_key=True)
    refscie = Column(String(16), ForeignKey("impiegato.cf"), nullable=False)
    resp = Column(String(16), ForeignKey("impiegato.cf"), nullable=False)
    nome = Column(String(20), unique=True, nullable=False)
    budget = Column(Numeric, nullable=True)
=======
from sqlalchemy import Column, String, Integer, Boolean, Date, Numeric, ForeignKey
from sqlalchemy.orm import declarative_base

Base = declarative_base()

class Impiegato(Base):
    __tablename__ = "impiegato"
    
    cf = Column(String(16),primary_key = True)
    nome = Column(String(20), nullable=False)
    cognome = Column(String(20), nullable=False)
    datanascita = Column(Date, nullable=False)
    merito = Column(Boolean, default=False)
    codicecon = Column(String(10), nullable=False, unique=True)
    dataassunzione = Column(Date, nullable=False)
    eta = Column(Integer)
    categoria = Column(String(20), nullable=False)
    salario = Column(Numeric, nullable=False)
    
class Promozione(Base):
    __tablename__ = "promozione"

    cf = Column(String(16), ForeignKey("impiegato.cf"), primary_key=True)
    codicecon = Column(String(10), primary_key=True)
    datapassaggio = Column(Date, nullable=False)
    nuovacategoria = Column(String(20), nullable=True)

class Laboratorio(Base):
    __tablename__ = "laboratorio"

    nome = Column(String(20), primary_key=True)
    respscie = Column(String(16), ForeignKey("impiegato.cf"), nullable=False)
    topic = Column(String(50), nullable=False)
    n_afferenti = Column(Integer, nullable=True)

class Progetto(Base):
    __tablename__ = "progetto"

    cup = Column(String(15), primary_key=True)
    refscie = Column(String(16), ForeignKey("impiegato.cf"), nullable=False)
    resp = Column(String(16), ForeignKey("impiegato.cf"), nullable=False)
    nome = Column(String(20), unique=True, nullable=False)
    budget = Column(Numeric, nullable=True)
>>>>>>> baf1004a42cc1d14a0452235af67ece2b3bb252e
