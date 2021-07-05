package org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.support;

import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.Interceptor;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.Stage;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.annotations.First;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.annotations.Last;

@First(stage = Stage.BEFORE)
@Last(stage = Stage.AFTER)
public class AInterceptor extends Interceptor { }
