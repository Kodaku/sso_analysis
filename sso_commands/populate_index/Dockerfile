FROM python:3.6-buster

WORKDIR /code

COPY ./sso_commands/populate_index/requirements.txt /code/requirements.txt

RUN apt-get -yy install libmariadb-dev

RUN pip install --no-cache-dir --upgrade -r /code/requirements.txt

COPY ./sso_commands/populate_index/app /code/app

CMD ["python", "./app/main.py"]