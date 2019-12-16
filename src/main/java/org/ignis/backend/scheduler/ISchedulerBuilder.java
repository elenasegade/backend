/*
 * Copyright (C) 2019 César Pomar
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.ignis.backend.scheduler;


/**
 *
 * @author César Pomar
 */
public class ISchedulerBuilder {

    public static IScheduler create(String type, String url) {
        if (type != null) {
            type = type.toLowerCase();
        }
        switch (type) {
            case "0":
                return new IMarathonScheduler(url);
            default:
                throw new RuntimeException("Scheduler " + type + " not found");//TODO change exception type    
        }
    }

}