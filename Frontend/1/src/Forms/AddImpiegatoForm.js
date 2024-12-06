import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { addImpiegato } from "../Services/ImpiegatoService";

const AddImpiegatoForm = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    cf: "",
    nome: "",
    cognome: "",
    datanascita: "",
    codicecon: "",
    merito: false,
    categoria: "Junior",
    salario: 1500.0,
    dataassunzione: new Date().toISOString().split("T")[0],
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const [loading, setLoading] = useState(false);

  const handleSubmit = (e) => {
    e.preventDefault();
  
    if (!formData.cf || !formData.nome || !formData.cognome || !formData.datanascita || !formData.codicecon) {
      alert("Tutti i campi obbligatori devono essere compilati.");
      return;
    }
  
    addImpiegato(formData)
      .then(() => {
        alert("Impiegato aggiunto con successo!");
        navigate("/impiegati");
      })
      .catch((err) => alert(`Errore: ${err.message}`));
  };
  
  
  return (
    <form onSubmit={handleSubmit}>
      <h1>Aggiungi Impiegato</h1>
      {loading && <p>Caricamento in corso...</p>}
      <input name="cf" placeholder="CF" value={formData.cf} onChange={handleChange} required />
      <input name="nome" placeholder="Nome" value={formData.nome} onChange={handleChange} required />
      <input name="cognome" placeholder="Cognome" value={formData.cognome} onChange={handleChange} required />
      <input name="datanascita" placeholder="Data di nascita (yyyy-mm-dd)" value={formData.datanascita} onChange={handleChange} required />
      <input name="codicecon" placeholder="Codice Contratto" value={formData.codicecon} onChange={handleChange} required />
      <label>
        Merito:
        <input
          name="merito"
          type="checkbox"
          checked={formData.merito}
          onChange={() => setFormData({ ...formData, merito: !formData.merito })}
        />
      </label>
      <button type="submit" disabled={loading}>Aggiungi</button>
    </form>
  );
}

export default AddImpiegatoForm;