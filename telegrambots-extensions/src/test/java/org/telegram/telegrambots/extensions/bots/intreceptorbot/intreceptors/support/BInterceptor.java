package org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.support;

import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.Interceptor;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.Stage;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.annotations.First;
import org.telegram.telegrambots.extensions.bots.intreceptorbot.intreceptors.annotations.Last;

@First(stage = Stage.AFTER)
@Last(stage = Stage.BEFORE)
public class BInterceptor extends Interceptor { }
