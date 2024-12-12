FROM python:3-slim-buster

WORKDIR /app

COPY ./requirements.txt /app/

RUN pip install --upgrade pip

RUN apt-get update && apt-get install -y git && apt-get clean

RUN pip install flask

RUN pip install --no-cache-dir -r /app/requirements.txt

COPY ./Backend_Python /app/Backend_Python
COPY ./Backend_SQL /app/Backend_SQL

ENV PYTHONPATH="/app:/app/Backend_Python"
ENV FLASK_APP=Backend_Python.main:app
ENV FLASK_RUN_HOST=0.0.0.0
ENV FLASK_RUN_PORT=5000

EXPOSE 5000

CMD ["flask", "run"]