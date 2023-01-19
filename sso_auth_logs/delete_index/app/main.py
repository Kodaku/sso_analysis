from elasticsearch import Elasticsearch

es = Elasticsearch(
        hosts=['http://localhost:9200'],
        basic_auth=('elastic', 'Cj-ChuXcllkRQF8t8VFa')
    )

index_name = "sso_auth_logs"

if es.indices.exists(index=index_name):
    print(f"Index {index_name} deleted")
    es.options(ignore_status=[400, 404]).indices.delete(index=index_name)
