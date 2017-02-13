/**
 * Copyright (c) 2016-2017, Mihai Emil Andronache
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * Neither the name of the copyright holder nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package com.amihaiemil.camel;

/**
 * Decorator for YamlLine to check if the line is well indented relative to the
 * previous one.
 * @author Sherif Waly (sherifwaly95@gmail.com)
 * @version $Id$
 * @since 1.0.0
 * @todo #55:30m/TEST Implement WellIndentedLineTest to unit test this class
 *
 */
final class WellIndentedLine implements YamlLine {

    /**
     * Content line.
     */
    private YamlLine line;
    
    /**
     * Previous line.
     */
    private YamlLine previousLine;
    
    /**
     * Ctor.
     * @param line YamlLine
     * @param previousLine YamlLine
     */
    WellIndentedLine(final YamlLine line, final YamlLine previousLine) {
        this.line = line;
        this.previousLine = previousLine;
    }
    
    @Override
    public String trimmed() {
        return this.line.trimmed();
    }
    
    @Override
    public int number() {
        return this.line.number();
    }

    @Override
    public int indentation() {
        final int lineIndent = this.line.indentation();
        final int previousLineIndent = this.previousLine.indentation();
        
        final String specialCharacters = ":>|-";
        final CharSequence prevLinelastChar = 
            this.previousLine.trimmed()
            .substring(this.previousLine.trimmed().length()-1);
        
        if(specialCharacters.contains(prevLinelastChar)) {
            if(lineIndent != previousLineIndent+2) {
                throw new IllegalStateException(
                    "Line indentation not shifted by 2"
                );
            }
        } else {
            if(lineIndent > previousLineIndent) {
                throw new IllegalStateException(
                    "Line indendation is more than the previous line"
                );
            }
        }
        return lineIndent;
    }
    
}