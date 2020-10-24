import xml.etree.ElementTree as ET

root = ET.parse('version.xml').getroot()

if not(root[0].tag == 'name'):
    raise AttributeError('First tag in version.xml must be name.')
if not(root[1].tag == 'author'):
    raise AttributeError('Second tag in version.xml must be author.')
if not(root[2].tag == 'updated-at'):
    raise AttributeError('Third tag in version.xml must be the update date.')

def getName():
    return root[0].text

def getAuthor():
    return root[1].text

def getUpdatedAt():
    return root[2].text

def getXMakeVersion():
    return root[4][1].text