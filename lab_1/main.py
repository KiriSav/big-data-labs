from constants.constants_main import *
from source_code.obfuscator import *
from source_code.worker_file import *

import argparse

parser = argparse.ArgumentParser()
parser.add_argument(NAME_ARGUMENT, type=int, help=HELP_VALUE, default=DEFAULT_VALUE, action=ACTION_VALUE)


def main():
    args = parser.parse_args()
    mode = args.mode

    worker_file = WorkerFile()
    obfuscator = Obfuscator()

    array_str_encoded = []
    array_str_decoded = []

    if mode == 1:
        array_str_source = worker_file.read_file(SOURCE_FILE_NAME)

        for string in array_str_source:
            str_encoder = obfuscator.obfuscate(string)
            array_str_encoded.append(str_encoder)

        worker_file.write_file(ENCODE_FILE_NAME, array_str_encoded)

    elif mode == 2:
        array_str_encoded = worker_file.read_file(ENCODE_FILE_NAME)

        for string in array_str_encoded:
            str_decoded = obfuscator.unobfuscate(string)
            array_str_decoded.append(str_decoded)

        worker_file.write_file(DECODE_FILE_NAME, array_str_decoded)

    else:
        print(ERROR_MESSAGE_INPUT_MODE)


if __name__ == '__main__':
    main()
