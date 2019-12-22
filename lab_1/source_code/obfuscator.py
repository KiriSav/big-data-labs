import base64

from constants.constants_obfuscator import *


class Obfuscator:
    def obfuscate(self, str_source):
        str_encoded = ""
        for symbol in str_source:
            if symbol not in SYMBOLS_ARRAY_NOT_ENCODED:
                symbol_bytes = symbol.encode()
                symbol_base_64_bytes = base64.b64encode(symbol_bytes)[:2]
                str_encoded += symbol_base_64_bytes.decode(ENCODING)
            else:
                str_encoded = str_encoded + symbol

        return str_encoded

    def unobfuscate(self, str_encoded):
        str_decoded = ""
        index = 0
        while index < len(str_encoded):
            symbol = ""
            if str_encoded[index] not in SYMBOLS_ARRAY_NOT_ENCODED:
                for i in range(0, SIZE_ONE_SYMBOL_BASE_64):
                    symbol += str_encoded[index + i]

                symbol_bytes = base64.b64decode(symbol + EMPTY_SYMBOLS_BASE_64)
                symbol_result = symbol_bytes.decode()
                str_decoded = str_decoded + symbol_result
                index += SIZE_ONE_SYMBOL_BASE_64
            else:
                str_decoded += str_encoded[index]
                index += 1

        return str_decoded
