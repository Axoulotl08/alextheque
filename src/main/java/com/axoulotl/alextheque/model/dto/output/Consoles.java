package com.axoulotl.alextheque.model.dto.output;

import com.axoulotl.alextheque.model.entity.Console;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Consoles {
    private List<Console> consoleList;
}
