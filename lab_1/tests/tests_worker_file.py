import sys
import hashlib

sys.path.insert(0, '../')

from twisted.trial import unittest
from constants.constants_test import *
from source_code.worker_file import *


def read_file(file_name):
    array_str = []
    try:
        file = open(file_name, 'r')
        for str in file:
            array_str.append(str)
        file.close()
        return array_str
    except IOError:
        print("Could not read file:", file_name)


def create_file_string(array):
    file_string = ''
    for element_array in array:
        file_string += element_array
    return file_string


class TestsWorkerFile(unittest.TestCase):
    def test_read_file(self):
        worker_file = WorkerFile()
        result_array_str = worker_file.read_file(PATH_READ_FILE)
        file_string = create_file_string(result_array_str)
        hash_string_sha1 = hashlib.sha1(file_string.encode())
        self.assertTrue(hash_string_sha1.hexdigest() == READ_FILE_RESULT_STRING_HASH)

    def test_write_file(self):
        worker_file = WorkerFile()
        worker_file.write_file(PATH_WRITE_FILE, ARRAY_WRITE_STRING)

        result_array_str = read_file(PATH_WRITE_FILE)

        file_string_write = create_file_string(ARRAY_WRITE_STRING)
        file_string_read = create_file_string(result_array_str)

        hash_string_write_sha1 = hashlib.sha1(file_string_write.encode())
        hash_string_read_sha1 = hashlib.sha1(file_string_read.encode())
        self.assertTrue(hash_string_read_sha1.hexdigest() == hash_string_write_sha1.hexdigest())


if __name__ == '__main__':
    unittest.main()
