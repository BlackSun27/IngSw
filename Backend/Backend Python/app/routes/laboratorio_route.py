from flask import Blueprint, request, jsonify
from controller.laboratorio_controller import LaboratorioController
from database import get_db

laboratorio_bp = Blueprint("laboratori", __name__, url_prefix="/api/laboratori")
db = next(get_db())
controller = LaboratorioController(db)

@laboratorio_bp.route("/", methods=["POST"])
def aggiungi_laboratorio():
    data = request.json
    
    try:
        controller.aggiungi_laboratorio(
            data["nome"],
            data["resp_sci"],
            data["topic"]
        )
        return jsonify({"message": "Laboratorio aggiunto con successo!"}), 201
    except Exception as e:
        return jsonify({"error": str(e)}), 400
    
@laboratorio_bp.route("/<nome>", methods = ["DELETE"])
def rimuovi_laboratorio(nome):
    try:
        controller.rimuovi_laboratorio(nome)
        return jsonify({"message": f"Laboratorio {nome} rimosso con successo!"}), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 400
    
@laboratorio_bp.route("/<nome_lab>", methods = ["GET"])
def carica_afferenze(nome_lab):
    try:
        controller.carica_afferenze(nome_lab)
        return jsonify({"message": "Afferenti del laboratorio {nome_lab} caricati con successo!"}), 200
    except Exception as e:
        return jsonify({"message" : str(e)}), 400
    
@laboratorio_bp.route("/<nome_lab>/responsabile", methods = ["GET"])
def carica_responsabile(nome_lab):
    try:
        responsabile = controller.carica_resp_sci(nome_lab)
        return jsonify({"Responsabile" : responsabile}), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 400
    
@laboratorio_bp.route("/<nome_lab>/progetti", methods=["GET"])
def carica_progetti(nome_lab):
    try:
        progetti = controller.carica_prog_lavora(nome_lab)
        return jsonify({"Progetti":progetti})
    except Exception as e:
        return jsonify({"error": str(e)})