import json
import pandas as pd


def build_dataframe(cur):
    query = "SELECT * FROM sso.comando c JOIN user_comando uc on c.tag = uc.cmd;"
    cur.execute(query)
    commands = []
    for result in cur:
        command_tag, command_name, command_type, level, _, command_state, _, username, _, _, user_id, user_state = result
        commands.append(
            {
                "id": command_tag + command_name + command_type,
                "command_tag": command_tag,
                "command_name": command_name,
                "command_type": command_type,
                "level": level,
                "command_state": command_state,
                "username": username,
                "user_id": user_id,
                "user_state": user_state
            }
        )
    commands_df = pd.read_json(json.dumps(commands))
    return preprocess_dataframe(commands_df)


def preprocess_dataframe(commands_df):
    for column in commands_df.columns:
        commands_df[column] = commands_df[column].replace("", None)
    commands_df.fillna("UK", inplace=True)
    return commands_df
