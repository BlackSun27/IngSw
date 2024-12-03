FROM python:3-slim-buster

WORKDIR /app

COPY ./requirements.txt ./

RUN pip install --upgrade pip

RUN apt-get update && apt-get install -y git && apt-get clean

RUN pip install -r requirements.txt

COPY ./Backend_Python /app/

ENTRYPOINT [ “python” ]

EXPOSE 4000

CMD [ "flask", "run", "--host:0.0.0.0", "--port:4000" ]