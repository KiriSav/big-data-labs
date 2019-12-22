from constants.constants_worker_file import *


class WorkerFile:
    def read_file(self, file_name):
        array_str = []
        try:
            file = open(file_name, 'r')
            for str in file:
                array_str.append(str)
            file.close()
            return array_str
        except IOError:
            print(ERROR_MESSAGE_OPEN_READ_FILE, file_name)

    def write_file(self, file_name, array_str):
        try:
            file = open(file_name, 'w')
            for str in array_str:
                file.write(str)
            file.close()
        except IOError:
            print(ERROR_MESSAGE_OPEN_WRITE_FILE, file_name)
