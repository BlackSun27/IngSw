from sqlalchemy import Column, String, Integer, Boolean, Date, Numeric, ForeignKey
from sqlalchemy.orm import declarative_base

Base = declarative_base()

class Impiegato(Base):
    __tablename__ = "impiegato"
    
    cf = Column(String(16),primary_key = True)
    nome = Column(String(20), nullable=False)
    cognome = Column(String(20), nullable=False)
    data_nascita = Column(Date, nullable=False)
    merito = Column(Boolean, default=False)
    codice_contratto = Column(String(10), nullable=False, unique=True)
    data_assunzione = Column(Date, nullable=False)
    eta = Column(Integer)
    categoria = Column(String(20), nullable=False)
    salario = Column(Numeric, nullable=False)
    
class Promozione(Base):
    __tablename__ = "promozione"

    cf = Column(String(16), ForeignKey("impiegato.cf"), primary_key=True)
    contratto = Column(String(10), primary_key=True)
    data_passaggio = Column(Date, nullable=False)
    vecchia_categoria = Column(String(20), nullable = True)
    nuova_categoria = Column(String(20), nullable=True)

class Laboratorio(Base):
    __tablename__ = "laboratorio"

    nome = Column(String(20), primary_key=True)
    responsabile_scientifico = Column(String(16), ForeignKey("impiegato.cf"), nullable=False)
    topic = Column(String(50), nullable=False)
    numero_afferenti = Column(Integer, nullable=True)

class Progetto(Base):
    __tablename__ = "progetto"

    cup = Column(String(15), primary_key=True)
    referente_scientifico = Column(String(16), ForeignKey("impiegato.cf"), nullable=False)
    responsabile = Column(String(16), ForeignKey("impiegato.cf"), nullable=False)
    nome = Column(String(20), unique=True, nullable=False)
    budget = Column(Numeric, nullable=True)