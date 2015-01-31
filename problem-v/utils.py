import logging
import re


logging.basicConfig(level=logging.WARN)
logger = logging.getLogger(__name__)


def parse_args(input, available_letters='abcde'):
    '''
    Parse string input to list of tuples, where tuple contains
    2, non equal, case insensitive, nodes and distance (>0) between them.

    :param input: Raw string input (eg. 'AB5, BC4, CD8, DC8, DE6').
    :param available_letters: Expected available node key values (atleast 2 unique letters).
    '''
    if len(set(available_letters)) < 2:
        raise ValueError('Available letters ({0}) need at least 2 unique values.'.
                         format(available_letters))
    args = []
    for arg in input.lower().replace(',', ' ').split():
        value = re.findall('[' + available_letters + ']{2}[\d]{1,}$', arg)
        warn_reason = None
        if value:
            output = re.findall('[' + available_letters + ']', value[0])
            distance = int(re.findall('[\d]{1,}', value[0])[0])
            if distance > 0 and output[0] != output[1]:
                output.append(distance)
                args.append(tuple(output))
            else:
                if distance == 0:
                    warn_reason = 'distance between nodes should be > 0.'
                else:
                    warn_reason = 'node names are equal, edge can be added only to different nodes.'
        else:
            warn_reason = 'invalid argument format.'
        if warn_reason:
            logger.warn('Invalid argument - {0}. Reason - {1}'.
                        format(arg, warn_reason))
    if args:
        return args
    return None


def read_input(msg):
    '''
    Read user input and return it.

    :param msg: Display message before user input.
    '''
    input_method = None
    try:
        # Python 2.x
        input_method = raw_input
    except Exception:
        # Python 3.x
        input_method = input
    input_arg = input_method(msg)
    if len(input_arg) > 0:
        return input_arg
    else:
        return None
