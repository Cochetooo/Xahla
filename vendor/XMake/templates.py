import os

TEMPLATE_LOCATION = 'vendor/templates'
WORKSPACE_FOLDER = 'workspace'
author = os.getlogin()

# Return the template with the given name.
def getTemplate(name, extension='.java', subdir='temp-'):
    return open(f'{TEMPLATE_LOCATION}/{subdir}{name}{extension}', 'r')

# Write a new file with a package and name and its content
def writeFile(package, objectName, content):
    newFile = open(f'{package}/{objectName}.java', 'w')
    newFile.write(content)
    print('Created file: {}'.format(package + "/" + objectName + ".java"))

# Compile the Blade Templates with a set of values to replace
def compileBlade(content, toReplace):
    result = ""
    while (content.find('{{') != -1):
        index = content.find('{{')
        result += content[0:index]
        content = content[index:]

        index = content.find('}}')
        value = toReplace[content[2:index].replace(' ', '')]
        result += value
        content = content[index+2:]
    
    result += content
    
    return result

# Generate a Java Controller File
def makeController(package, objectName, crud=False):
    template = None

    if (crud):
        template = getTemplate('CRUDController')
    else:
        template = getTemplate('Controller')
    
    content = compileBlade(template.read(), {'package':package, 'objectName':objectName})

    writeFile(WORKSPACE_FOLDER + '/src/' + package, objectName, content)

def makeMainXahla(programName):
    template = getTemplate('MainXahla')

    content = compileBlade(template.read(), {'author':author, 'programName':programName})

    writeFile(WORKSPACE_FOLDER + '/' + programName + '/src/org/xahla/main', programName, content)

def makeMainXahlaFB(programName):
    template = getTemplate('MainXahlaFB')

    content = compileBlade(template.read(), {'author':author, 'programName':programName})

    writeFile(WORKSPACE_FOLDER + '/' + programName + '/src/org/xahla/main', programName, content)

# Generate a Java Sample XObject File
def makeXObject(package, objectName):
    template = getTemplate('XObject')

    content = compileBlade(template.read(), {'package':package.replace('/', '.'), 'author':author, 'objectName':objectName})

    writeFile(WORKSPACE_FOLDER + '/src/' + package, objectName, content)
