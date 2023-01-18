from constants import possible_events, possible_descriptions
from enums import AuthLogEvent, FailureCause


def categorize_event(event):
    if event in possible_events:
        if event == possible_events[0]:
            return AuthLogEvent.LOGIN_FAILED.name
        if event == possible_events[1]:
            return AuthLogEvent.LOGIN_SUCCESSFUL.name
        if event == possible_events[2]:
            return AuthLogEvent.EXPIRED_TOKEN.name
        if event == possible_events[3]:
            return AuthLogEvent.TOKEN_VALIDATED.name
        if event == possible_events[4]:
            return AuthLogEvent.VALIDATION_TOKEN.name
    return AuthLogEvent.UNKNOWN.name


def categorize_descr(descr):
    if descr in possible_descriptions:
        if descr == possible_descriptions[0]:
            return FailureCause.WRONG_CREDENTIALS.name
        if descr == possible_descriptions[1]:
            return FailureCause.BLOCKED_USER.name
        if descr == possible_descriptions[2]:
            return FailureCause.EXPIRED_TOKEN.name
    else:
        return FailureCause.UNKNOWN.name