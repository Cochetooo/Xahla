import sys
import xmlParser as xml
import templates as tmp
import create as crt

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
    elif (argv[0] == 'create:gui'):
        if (len(argv) < 2):
            print('The command must have atleast 2 arguments.')
        else:
            pass

    # Create a new Server Xahla Project
    elif (argv[0] == 'create:server'):
        if (len(argv) < 2):
            print('The command must have atleast 2 arguments.')
        else:
            pass

    # Create a new Xahla Project
    elif (argv[0] == 'create:xahla'):
        if (len(argv) < 2):
            print('The command must have atleast 2 arguments.')
        else:
            crt.createXahla(argv[1])

    elif (argv[0] == 'make:controller'):
        if (len(argv) < 3):
            print('The command must have atleast 3 arguments.')
        else:
            if (len(argv) > 3):
                if (argv[3] == '--crud'):
                    tmp.makeController(argv[1], argv[2], True)
                else:
                    print('Error at argument 3: Invalid Argument ({0})'.format(argv[3]))
            else:
                tmp.makeController(argv[1], argv[2])

    elif (argv[0] == 'make:xobject'):
        if (len(argv) < 3):
            print('The command must have atleast 3 arguments.')
        else:
            tmp.makeXObject(argv[1], argv[2])
    

# Main Function
if __name__ == '__main__':
    if (len(sys.argv) < 1):
        start_cmd()
    else:
        compute(sys.argv[1:])
