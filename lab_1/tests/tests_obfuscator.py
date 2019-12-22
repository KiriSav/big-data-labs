import unittest
import sys

sys.path.insert(0, '../')

from constants.constants_test import *
from source_code.obfuscator import *


class TestsObfuscator(unittest.TestCase):

    def test_obfuscate(self):
        obfuscator = Obfuscator()
        encode_string = obfuscator.obfuscate(SOURCE_STRING)
        self.assertTrue(encode_string == ENCODE_STRING)

    def test_unobfuscate(self):
        obfuscator = Obfuscator()
        source_string = obfuscator.unobfuscate(ENCODE_STRING)
        self.assertTrue(source_string == SOURCE_STRING)


if __name__ == '__main__':
    unittest.main()
