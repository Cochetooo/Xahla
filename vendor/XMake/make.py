import sys
import xmlParser as xml
import templates as tmp
import create as crt

NOT_ENOUGH_ARG = 'This command must contains atleast {} arguments.'

# Start Console In I/O Mode
def start_cmd():
    pass

# Compute Arguments From Command Line
def compute(argv):
    # Help Index
    if (argv[0] == '--help' or argv[0] == '-h' or argv[0] == '?'):
        print("----------- \\ Help (1/1) / -----------")
        
    # Get Version
    elif (argv[0] == '--version' or argv[0] == '-v'):
        print("XMake Version: {0}".format(xml.getXMakeVersion()))

    # Create a new GUI Xahla Project
    elif (argv[0] == 'create:xahla-gui'):
        if (len(argv) < 2):
            print(NOT_ENOUGH_ARG.format('2'))

    # Create a new Server Xahla Project
    elif (argv[0] == 'create:xahla-server'):
        if (len(argv) < 2):
            print(NOT_ENOUGH_ARG.format('2'))

    # Create a new Xahla Project
    elif (argv[0] == 'create:xahla-core'):
        if (len(argv) < 2):
            print(NOT_ENOUGH_ARG.format('2'))
        else:
            crt.createXahla(argv[1])

    # Make a Controller for XDataManager
    elif (argv[0] == 'make:controller'):
        if (len(argv) < 3):
            print(NOT_ENOUGH_ARG.format('3'))
        else:
            if (len(argv) > 3):
                if (argv[3] == '--crud'):
                    tmp.makeController(argv[1], argv[2], True)
                else:
                    print('Error at argument 3: Invalid Argument ({})'.format(argv[3]))
            else:
                tmp.makeController(argv[1], argv[2])

    # Make a XObject for Core Xahla Project
    elif (argv[0] == 'make:xobject'):
        if (len(argv) < 3):
            print(NOT_ENOUGH_ARG.format('3'))
        else:
            tmp.makeXObject(argv[1], argv[2])
    

# Main Function
if __name__ == '__main__':
    if (len(sys.argv) < 1):
        start_cmd()
    else:
        compute(sys.argv[1:])
