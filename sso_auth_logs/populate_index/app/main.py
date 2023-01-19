from elasticsearch import Elasticsearch
from elasticsearch.exceptions import RequestError
from connector import connect_to_sso_db
from dataframe_building import build_dataframe
import json
import pandas as pd


def es_create_index_if_not_exists(es, index):
    try:
        es.indices.create(index=index)
    except RequestError as ex:
        print(ex)


class PdEncoder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj, pd.Timestamp):
            return str(obj)
        return json.JSONEncoder.default(self, obj)


if __name__ == "__main__":
    cur = connect_to_sso_db()
    auth_logs_df = build_dataframe(cur)

    es = Elasticsearch(
        hosts=['http://host.docker.internal:9200'],
        basic_auth=('elastic', 'Cj-ChuXcllkRQF8t8VFa')
    )

    index_name = "sso_auth_logs"

    if not es.indices.exists(index=index_name):
        print(f"Index {index_name} created")
        es_create_index_if_not_exists(es, index_name)

    df_types = {}
    columns = auth_logs_df.columns
    dtypes = auth_logs_df.dtypes
    for column, column_type in zip(columns, dtypes):
        df_types[column] = column_type

    actions = []
    index = 1
    for i, command_row in auth_logs_df.iterrows():
        command = {}
        for column in auth_logs_df.columns:
            command[column] = command_row[column]
        action = {"index": {"_index": index_name, "_id": index}, "_op_type": "upsert"}
        doc = command
        actions.append(action)
        actions.append(json.dumps(doc, cls=PdEncoder))
        index += 1

    res = es.bulk(index=index_name, operations=actions)
    print(res)
    # print(sys.argv)
