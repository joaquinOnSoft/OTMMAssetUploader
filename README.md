# OTMMAssetUploader

Command line tool to upload a file/s to an OpenText Media Management (OTMM) instance
using the [OTMM REST API](https://developer.opentext.com/apis/14ba85a7-4693-48d3-8c93-9214c663edd2/06c4a79f-3f4a-4a5a-aab9-9519740b27c7/1d6ec9c5-7620-456e-b52f-cfffb2734eb0).

> **NOTE**: This project is a technological demonstrator 
> to show how to use OTMM REST API. 
> 
> It doesn't pretend to be a production-ready tool.

## What's OTMM?

[OpenText™ Media Management](https://www.opentext.com/products-and-solutions/products/customer-experience-management/digital-asset-management/opentext-media-management) 
is a leading, innovative and scalable DAM solution that optimizes rich media 
asset management, use and distribution across the digital ecosystem. From 
creation to consumption, Media Management offers a consolidated asset 
repository for marketing, branding, commerce, video and global distribution 
across contributors and production environments.


### otmm-api.properties

A *configuration file* that contains the information needed to access the OTMM REST API.
It contains the following parameters:

 - **url**: OpenText Media Management REST API URL
 - **version**: OpenText Media Management REST API version. Default value 6
 - **user**: OTMM user
 - **password**: OTMM password for the given user

Additionally, you can add these parameters if you want to run the Unit Test in your environment:

 - **folder.id.my.folders.sample**: Folder identifier e.g. `My Folders > sample` (*6f40a265cc535ccfc848364eec9ab1220e909c76* in my environment) used to upload asset in unit test
 - **collection.id.sample**: Collection identifier, e.g. `London` (*36c871c26551f86c25c54f9671dcca1eb8ed9cf6* in my environment) to check the content of a collection in unit test

```
url=http://mydomainexmple.com:11090
version=6
user=tsuper
password=<PASSORD_GOES_HERE>
```

## Subprojects

### otmm-assets-uploader-core

A base project that contains all the common code. Mainly, the OTMM API wrapper and some utility classes.

This project is used and referenced by the other subprojects.

### otmm-assets-uploader-demo

Demo project to show how to upload an asset from the command line using the OTMM API.

#### How to run it?

The project include an autoexecutale jar file 

It accept the following parameters:

   - -c --config  <CONFIG_FILE> 
   - -a --asset   Asset file. If a directory is specified all the .png and .jgp will be uploaded.

Execution example:

```
java -jar OTMMAssetUploader-demo-23.03.27.jar --config otmm-api.properties --asset ./the-red-or-white-cat-i-on-white-studio.jpg
```

### otmm-assets-uploader-coins