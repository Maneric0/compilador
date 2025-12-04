.assembly extern mscorlib {}
.assembly _programa{}
.module _programa.exe

.class public _unica{
.method static public void _principal(){
.entrypoint{
ldstr 
"fafa"
call void [mscorlib]System.Console::Write(string)
ret
}
}
}
