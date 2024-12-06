import React, { useState, useEffect } from "react";
import { getPromozioni, caricaAfferenze, getImpiegati, caricaProgetti, rimuoviImpiegato, promuoviImpiegato, addImpiegato } from "./ImpiegatoService";
import { useNavigate } from "react-router-dom";

const ImpiegatiList = () => {
  const [impiegati, setImpiegati] = useState([]);
  const [selectedCF, setSelectedCF] = useState(null);
  const [promozioni, setPromozioni] = useState([]);
  const [afferenze, setAfferenze] = useState([]);
  const [progetto, setProgetto] = useState([]);
  const [showAddForm, setShowAddForm] = useState(false);
  const [newImpiegato, setNewImpiegato] = useState({
    cf: "",
    nome: "",
    cognome: "",
    datanascita: "",
    codicecon: "",
    dataassunzione: new Date().toISOString().split("T")[0],
  });

  const navigate = useNavigate();

  useEffect(() => {
    getImpiegati()
      .then(setImpiegati)
      .catch((err) => console.error("Errore:", err));
  }, []);

  const handleSelectCF = async (cf) => {
    setSelectedCF(cf);

    try {
      const promozioniData = await getPromozioni(cf);
      setPromozioni(promozioniData);

      const afferenzeData = await caricaAfferenze(cf);
      setAfferenze(afferenzeData);

      const progettoData = await caricaProgetti(cf);
      setProgetto(progettoData);
    } catch (error) {
      if (error.message.includes("404")) {
        navigate("/not-found", { state: { message: "L'impiegato selezionato non è stato trovato." } });
      } else {
        console.error(error.message);
      }
    }
  };

  const handleRimuoviImpiegato = async () => {
    if (!selectedCF) return alert("Seleziona un impiegato per rimuoverlo!");
    try {
      await rimuoviImpiegato(selectedCF);
      setSelectedCF(null);
      const updatedImpiegati = await getImpiegati();
      setImpiegati(updatedImpiegati);
      setPromozioni([]);
      setAfferenze([]);
    } catch (error) {
      if (error.message.includes("404")) {
        navigate("/not-found", { state: { message: "L'impiegato che stai tentando di rimuovere non esiste." } });
      } else {
        alert(`Errore nella rimozione dell'impiegato: ${error.message}`);
      }
    }
  };

  const handlePromuoviImpiegato = async () => {
    if (!selectedCF) return alert("Seleziona un impiegato per promuoverlo!");
    try {
      await promuoviImpiegato(selectedCF);
      const updatedPromozioni = await getPromozioni(selectedCF);
      setPromozioni(updatedPromozioni);
    } catch (error) {
      if (error.message.includes("404")) {
        navigate("/not-found", { state: { message: "Non è possibile promuovere l'impiegato selezionato perché non esiste." } });
      } else {
        alert(`Errore nella promozione dell'impiegato: ${error.message}`);
      }
    }
  };

  const handleAggiungiImpiegato = async (e) => {
    e.preventDefault();
    const impiegatoDaAggiungere = {
      ...newImpiegato,
      merito: false,
      categoria: "Junior",
      salario: 1500.0,
    };
    try {
      await addImpiegato(impiegatoDaAggiungere);
      alert("Impiegato aggiunto con successo!");
      setNewImpiegato({
        cf: "",
        nome: "",
        cognome: "",
        datanascita: "",
        codicecon: "",
        dataassunzione: new Date().toISOString().split("T")[0],
      });
      setShowAddForm(false);
      getImpiegati().then(setImpiegati);
    } catch (error) {
      if (error.message.includes("404")) {
        navigate("/not-found", { state: { message: "Errore durante l'aggiunta: impiegato non trovato." } });
      } else {
        alert(`Errore nell'aggiunta dell'impiegato: ${error.message}`);
      }
    }
  };

  return (
    <div class = "scrollable-table">
      <div>
        <h1>Lista Impiegati</h1>
        <table border="1">
          <thead>
            <tr>
              <th>CF</th>
              <th>Nome</th>
              <th>Cognome</th>
              <th>Categoria</th>
              <th>Data Assunzione</th>
              <th>Data di Nascita</th>
              <th>Età</th>
              <th>Salario</th>
              <th>Merito</th>
            </tr>
          </thead>
          <tbody>
            {impiegati.map((impiegato) => (
              <tr key={impiegato.cf}>
                <td>{impiegato.cf}</td>
                <td>{impiegato.nome}</td>
                <td>{impiegato.cognome}</td>
                <td>{impiegato.categoria}</td>
                <td>{impiegato.dataassunzione}</td>
                <td>{impiegato.datanascita}</td>
                <td>{impiegato.eta}</td>
                <td>{impiegato.salario}</td>
                <td>{impiegato.merito ? "Sì" : "No"}</td>
                <td>
                  <button onClick={() => handleSelectCF(impiegato.cf)}>Mostra Dettagli</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <button onClick={() => setShowAddForm((prev) => !prev)}>
        {showAddForm ? "Annulla Aggiunta" : "Aggiungi Nuovo Impiegato"}
      </button>

      {showAddForm && (
        <form onSubmit={handleAggiungiImpiegato} style={{ marginTop: "20px" }}>
          <h2>Aggiungi Impiegato</h2>
          <input
            name="cf"
            placeholder="Codice Fiscale"
            value={newImpiegato.cf}
            onChange={(e) => setNewImpiegato({ ...newImpiegato, cf: e.target.value })}
            required
          />
          <input
            name="nome"
            placeholder="Nome"
            value={newImpiegato.nome}
            onChange={(e) => setNewImpiegato({ ...newImpiegato, nome: e.target.value })}
            required
          />
          <input
            name="cognome"
            placeholder="Cognome"
            value={newImpiegato.cognome}
            onChange={(e) => setNewImpiegato({ ...newImpiegato, cognome: e.target.value })}
            required
          />
          <input
            name="datanascita"
            type="date"
            placeholder="Data di Nascita"
            value={newImpiegato.datanascita}
            onChange={(e) => setNewImpiegato({ ...newImpiegato, datanascita: e.target.value })}
            required
          />
          <input
            name="codicecon"
            placeholder="Codice Contratto"
            value={newImpiegato.codicecon}
            onChange={(e) => setNewImpiegato({ ...newImpiegato, codicecon: e.target.value })}
            required
          />
          <button type="submit">Aggiungi Impiegato</button>
        </form>
      )}

      {selectedCF && (
        <div class = "scrollable-table">
          <h2>Dettagli per {selectedCF}</h2>

          <h3>Promozioni</h3>
          <table border="1">
            <thead>
              <tr>
                <th>Categoria</th>
                <th>Data Passaggio</th>
              </tr>
            </thead>
            <tbody>
              {promozioni.map((promo, index) => (
                <tr key={index}>
                  <td>{promo.categoria}</td>
                  <td>{promo.datapassaggio}</td>
                </tr>
              ))}
            </tbody>
          </table>

          <h3>Progetti e Afferenze</h3>
          <table border="1">
            <thead>
              <tr>
                <th>Nome Laboratorio</th>
                <th>CUP Progetto</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>{afferenze.nomelab}</td>
                <td>{progetto.cup}</td>
              </tr>
            </tbody>
          </table>
        </div>
      )}

      <div>
        <button onClick={handlePromuoviImpiegato}>Promuovi</button>
        <button onClick={handleRimuoviImpiegato}>Rimuovi</button>
      </div>
    </div>
  );
};

export default ImpiegatiList;