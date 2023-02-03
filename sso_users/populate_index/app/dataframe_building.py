import json
import pandas as pd


def build_dataframe(conn, cur):
    users_query = "SELECT * FROM sso.`user`;"
    cur.execute(users_query)
    results = cur.fetchall()
    cur.close()
    users = []
    for result in results:
        user_id, username, _, _, _, is_staff, name, surname, email, _, _, user_state, _, user_type, _ = result
        avatar_cur = conn.cursor()
        users_avatar_query = f"SELECT * FROM sso.`user` u JOIN sso.`file` f ON u.username = f.username WHERE u.username = '{username}';"
        avatar_cur.execute(users_avatar_query)
        user = {
            "id": username + str(user_id),
            "user_id": user_id,
            "username": username,
            "is_staff": True if is_staff == 1 else False,
            "name": name,
            "surname": surname,
            "email": email,
            "user_state": user_state,
            "user_type": user_type,
            "has_avatar": False
        }
        for avatar in avatar_cur:
            user["has_avatar"] = True
        users.append(user)
        avatar_cur.close()

    users_df = pd.read_json(json.dumps(users))
    return preprocess_dataframe(users_df)


def preprocess_dataframe(users_df):
    for column in users_df.columns:
        users_df[column] = users_df[column].replace("", None)
    users_df.fillna("UK", inplace=True)
    return users_df
