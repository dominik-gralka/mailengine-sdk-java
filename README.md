# MailEngine SDK

## Overview

The MailEngine SDK provides functionality to send emails using an HTTP client. It is designed to be simple and easy to use, with comprehensive error handling and logging.

## Features

- Send emails with detailed information about the sender and recipient.
- Handles API error responses and logs the details.
- Uses Jackson for JSON serialization and deserialization.
- Utilizes Apache HttpClient for making HTTP requests.

## Installation

Add the following dependency to your `pom.xml` file:

```xml
<dependency>
    <groupId>com.umbratic</groupId>
    <artifactId>mailengine</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
