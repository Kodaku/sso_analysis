import json
import pandas as pd
from utils import categorize_descr, categorize_event
from enums import AuthLogEvent, FailureCause


def build_dataframe(cur):
    query = "SELECT * from sso.auth_log;"
    cur.execute(query)
    auth_logs = []
    for result in cur:
        _, livello, created_at, descr, username, _, event, remote_host = result
        auth_logs.append({
            "livello": livello,
            "created_at": str(created_at),
            "failure_cause": categorize_descr(descr),
            "username": username,
            "event": categorize_event(event),
            "remote_host": remote_host
        }
        )
    auth_logs_df = pd.read_json(json.dumps(auth_logs))
    return preprocess_dataframe(auth_logs_df)


def should_analyze_failure_cause(row):
    if row['failure_cause'] == FailureCause.UNKNOWN.name:
        if row['event'] != AuthLogEvent.LOGIN_FAILED.name:
            return False
    return True


def preprocess_dataframe(auth_logs_df):
    for column in auth_logs_df.columns:
        auth_logs_df[column] = auth_logs_df[column].replace("", None)
    auth_logs_df.fillna("UK", inplace=True)
    auth_logs_df['should_analyze_failure_cause'] = False
    auth_logs_df['should_analyze_failure_cause'] = auth_logs_df.apply(should_analyze_failure_cause, axis=1)
    return auth_logs_df
