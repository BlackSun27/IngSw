import React, { useState, useEffect } from "react";
import {
  getLaboratori,
  aggiungiLaboratori,
  rimuoviImpiegato,
  caricaAfferenze,
  caricaResponsabile,
  caricaProgetti,
} from "./LaboratoriService";
import { useNavigate } from "react-router-dom";

const LaboratoriList = () => {
  const navigate = useNavigate();
  const [laboratori, setLaboratori] = useState([]);
  const [selectedLab, setSelectedLab] = useState(null);
  const [afferenti, setAfferenti] = useState([]);
  const [responsabile, setResponsabile] = useState("");
  const [progetto, setProgetto] = useState("");
  const [showAddForm, setShowAddForm] = useState(false);
  const [newLaboratorio, setNewLaboratorio] = useState({
    nome: "",
    responsabile_sci: "",
    topic: "",
    numero_afferenti: 0,
  });

  useEffect(() => {
    getLaboratori()
      .then(setLaboratori)
      .catch((error) => {
        if (error.message.includes("404")) {
          navigate("/not-found", { state: { message: "Nessun laboratorio trovato." } });
        } else {
          console.error("Errore:", error.message);
        }
      });
  }, [navigate]);

  const handleSelectLab = async (nome) => {
    setSelectedLab(nome);
    try {
      const afferenze = await caricaAfferenze(nome);
      setAfferenti(afferenze);

      const responsabileData = await caricaResponsabile(nome);
      setResponsabile(responsabileData);

      const progettoData = await caricaProgetti(nome);
      setProgetto(progettoData);
    } catch (error) {
      if (error.message.includes("404")) {
        navigate("/not-found", { state: { message: `Il laboratorio ${nome} non è stato trovato.` } });
      } else {
        console.error(error.message);
      }
    }
  };

  const handleRimuoviLaboratorio = async () => {
    if (!selectedLab) return alert("Seleziona un laboratorio per rimuoverlo!");
    try {
      await rimuoviImpiegato(selectedLab);
      setSelectedLab(null);
      const updatedLaboratori = await getLaboratori();
      setLaboratori(updatedLaboratori);
      setAfferenti([]);
      setResponsabile("");
      setProgetto("");
    } catch (error) {
      if (error.message.includes("404")) {
        navigate("/not-found", { state: { message: `Il laboratorio ${selectedLab} non è stato trovato.` } });
      } else {
        alert(`Errore nella rimozione del laboratorio: ${error.message}`);
      }
    }
  };

  const handleAggiungiLaboratorio = async (e) => {
    e.preventDefault();
    try {
      await aggiungiLaboratori(newLaboratorio);
      alert("Laboratorio aggiunto con successo!");
      setNewLaboratorio({
        nome: "",
        responsabile_sci: "",
        topic: "",
        numero_afferenti: 0,
      });
      setShowAddForm(false);
      getLaboratori().then(setLaboratori);
    } catch (error) {
      if (error.message.includes("404")) {
        navigate("/not-found", { state: { message: "Impossibile aggiungere il laboratorio. Verifica i dati." } });
      } else {
        alert(`Errore nell'aggiunta del laboratorio: ${error.message}`);
      }
    }
  };

  return (
    <div className = "scrollable-table">
      <div>
        <h1>Lista Laboratori</h1>
        <table border="1">
          <thead>
            <tr>
              <th>Nome</th>
              <th>Responsabile Scientifico</th>
              <th>Topic</th>
              <th>Numero Afferenti</th>
            </tr>
          </thead>
          <tbody>
            {laboratori.map((lab) => (
              <tr key={lab.nome}>
                <td>{lab.nome}</td>
                <td>{lab.responsabile_sci}</td>
                <td>{lab.topic}</td>
                <td>{lab.numero_afferenti}</td>
                <td>
                  <button onClick={() => handleSelectLab(lab.nome)}>
                    Mostra Dettagli
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {showAddForm && (
        <form onSubmit={handleAggiungiLaboratorio} style={{ marginTop: "20px" }}>
          <h2>Aggiungi Laboratorio</h2>
          <input
            name="nome"
            placeholder="Nome Laboratorio"
            value={newLaboratorio.nome}
            onChange={(e) =>
              setNewLaboratorio({ ...newLaboratorio, nome: e.target.value })
            }
            required
          />
          <input
            name="responsabile_sci"
            placeholder="Responsabile Scientifico"
            value={newLaboratorio.responsabile_sci}
            onChange={(e) =>
              setNewLaboratorio({
                ...newLaboratorio,
                responsabile_sci: e.target.value,
              })
            }
            required
          />
          <input
            name="topic"
            placeholder="Topic"
            value={newLaboratorio.topic}
            onChange={(e) =>
              setNewLaboratorio({ ...newLaboratorio, topic: e.target.value })
            }
            required
          />
          <input
            name="numero_afferenti"
            type="number"
            placeholder="Numero Afferenti"
            value={newLaboratorio.numero_afferenti}
            onChange={(e) =>
              setNewLaboratorio({
                ...newLaboratorio,
                numero_afferenti: Number(e.target.value),
              })
            }
            required
          />
          <button type="submit">Aggiungi Laboratorio</button>
        </form>
      )}

      {selectedLab && (
        <div className = "scrollable-table">
          <h2>Dettagli per {selectedLab}</h2>
          <h3>Afferenze</h3>
          <ul>
            {afferenti.map((cf, index) => (
              <li key={index}>{cf}</li>
            ))}
          </ul>
          <h3>Responsabile</h3>
          <p>{responsabile}</p>
          <h3>Progetto</h3>
          <p>{progetto}</p>
        </div>
      )}

      <div>
        <button onClick={handleRimuoviLaboratorio}>Rimuovi Laboratorio</button>
        <button onClick={() => setShowAddForm((prev) => !prev)}>
          {showAddForm ? "Annulla Aggiunta" : "Aggiungi Laboratorio"}
        </button>
      </div>
    </div>
  );
};

export default LaboratoriList;