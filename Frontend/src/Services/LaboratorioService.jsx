import handleAPIResponse from "C:/Users/admin/Desktop/Appunti/INGSW/INGSW2324_52/Frontend/src/Services/handleAPIresponse.jsx";

const API_URL = "http://localhost:4000/api/laboratori";
const getEncodedURL = (path, nome) => `${API_URL}/${encodeURIComponent(nome)}${path}`;

export const getLaboratori = async () => {
    try {
        const response = await fetch(`http://127.0.0.1:4000/api/gestionale/laboratori`);
        return handleAPIResponse(response, "Errore nel prelievo dei laboratori");
    } catch (error) {
        console.error("Errore nella chiamata API:", error);
        throw error;
    }
};

export const aggiungiLaboratori = async (laboratorio) => {
    try {
        const response = await fetch(`${API_URL}/`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(laboratorio),
        });
        return handleAPIResponse(response, "Errore nell'aggiunta del laboratorio");
    } catch (error) {
        console.error("Errore nella chiamata API:", error);
        throw error;
    }
};

export const rimuoviLaboratorio = async (nome) => {
    try {
        const url = getEncodedURL("", nome);
        const response = await fetch(url, {
            method: "DELETE",
        });
        return handleAPIResponse(response, "Errore nella rimozione del laboratorio");
    } catch (error) {
        console.error("Errore nella chiamata API:", error);
        throw error;
    }
};

export const caricaAfferenze = async (nome) => {
    try {
        const response = await fetch(`${API_URL}/${nome}`, {
            method : "GET",
        });
        return handleAPIResponse(response, "Errore nel recupero degli impiegati che lavorano al laboratorio");
    } catch (error) {
        console.error("Errore nella chiamata API:", error);
        throw error;
    }
};

export const caricaResponsabile = async (nome) => {
    try {
        const url = getEncodedURL("/responsabile", nome);
        const response = await fetch(url);
        return handleAPIResponse(response, "Errore nel recupero del responsabile del laboratorio");
    } catch (error) {
        console.error("Errore nella chiamata API:", error);
        throw error;
    }
};