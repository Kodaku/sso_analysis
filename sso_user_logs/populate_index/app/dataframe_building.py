import json
import pandas as pd


def build_dataframe(cur):
    query = "select * from sso.`user` u join sso.user_log ul on u.username = ul.username;"
    cur.execute(query)
    user_logs = []
    for result in cur:
        _, username, password_hash, secret, additional_security, is_staff, name, surname, email, last_update_ts, \
            disabled_until_ts, state, password_expiration_ts, user_type, origin, \
            row_id, operator_id, created_at, operator_name, update_data, username, operation_id, _, _, _, _, _ = result
        user_logs.append(
            {
                "operator_id": operator_id,
                "created_at": str(created_at),
                "operator_name": operator_name,
                "username": username,
                "operation_type": operation_id,
                "is_staff": True if is_staff == 1 else False
            }
        )
    user_logs_df = pd.read_json(json.dumps(user_logs))
    return preprocess_dataframe(user_logs_df)


def preprocess_dataframe(user_logs_df):
    for column in user_logs_df.columns:
        user_logs_df[column] = user_logs_df[column].replace("", None)
    user_logs_df.fillna("UK", inplace=True)
    return user_logs_df
