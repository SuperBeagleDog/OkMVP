This is a simply basic framework, which including both OkHttp and MVP.

Anyway, you should not change anything in this folder.
But, you can code according to the designing of it.

For instance:
 1, The 'mvp' folder shows you how to organize your code as MVP does.
 2, The 'okhttp' folder is enclosed the OkHttp for you, you should not change it,
 If you want to change anything about NetWork framework(OkHttp), you can change it in your code.
 for example, creates a HttpManager for managing and changing the NetWork framework.

Additionally, There are two things I have to say:

1, Why do I create 'interfaces' folders for views and presenters?

Because If do so, All of what views and presenters can do,
Both are defined in interfaces. This should be useful for maintaining and reading.

2, You should not add your beans in 'bean' and 'utils' folder, These folders are just like others.