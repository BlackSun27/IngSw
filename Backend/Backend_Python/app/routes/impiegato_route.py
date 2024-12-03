from flask import Blueprint, request, jsonify
from app.controller.impiegato_controller import ImpiegatoController
from database import get_db

impiegato_bp = Blueprint("impiegato", __name__, url_prefix="/api/impiegati")
db = next(get_db())
controller = ImpiegatoController(db)

@impiegato_bp.route("/", methods=["POST"])
def aggiungi_impiegato():
    data = request.json
    try:
        controller.aggiungi_impiegato(
            data["cf"], data["nome"], data["cognome"], data["datanascita"], data["merito"],
            data["codicecon"], data["dataassunzione"], data["categoria"], data["salario"]
        )
        return jsonify({"message": "Impiegato aggiunto con successo"}), 201
    except Exception as e:
        return jsonify({"error": str(e)}), 400

@impiegato_bp.route("/<string:cf>", methods=["DELETE"])
def rimuovi_impiegato(cf):
    try:
        controller.rimuovi_impiegato(
            cf
        )
        return jsonify({"message": "Impiegato rimosso con successo"}), 200
    except Exception as e:
        return jsonify({"error":str(e)}), 400
    
@impiegato_bp.route("/<string:cf>/promuovi", methods = ["PATCH"])
def promuovi_impiegato(cf):
    data = request.json
    try:
        controller.promuovi_impiegato(cf, data["merito"])
        return jsonify({"message": f"Impiegato con CF {cf} promosso"}), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 400
    
@impiegato_bp.route("<string:cf>/laboratori", methods=["GET"])
def carica_afferenze(cf):
    try:
        laboratori = controller.carica_afferenze(cf)
        return jsonify(laboratori), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 400